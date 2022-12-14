package cn.cleanarch.dmp.module.product.service.spu;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.framework.common.util.collection.CollectionUtils;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.ProductPropertyViewRespVO;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.property.ProductPropertyRespVO;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.value.ProductPropertyValueRespVO;
import cn.cleanarch.dmp.module.product.controller.admin.sku.vo.ProductSkuBaseVO;
import cn.cleanarch.dmp.module.product.controller.admin.sku.vo.ProductSkuCreateOrUpdateReqVO;
import cn.cleanarch.dmp.module.product.controller.admin.sku.vo.ProductSkuRespVO;
import cn.cleanarch.dmp.module.product.controller.admin.spu.vo.*;
import cn.cleanarch.dmp.module.product.controller.app.spu.vo.AppSpuPageReqVO;
import cn.cleanarch.dmp.module.product.controller.app.spu.vo.AppSpuPageRespVO;
import cn.cleanarch.dmp.module.product.convert.sku.ProductSkuConvert;
import cn.cleanarch.dmp.module.product.convert.spu.ProductSpuConvert;
import cn.cleanarch.dmp.module.product.dal.dataobject.sku.ProductSkuDO;
import cn.cleanarch.dmp.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.cleanarch.dmp.module.product.dal.mysql.spu.ProductSpuMapper;
import cn.cleanarch.dmp.module.product.enums.spu.ProductSpuSpecTypeEnum;
import cn.cleanarch.dmp.module.product.service.brand.ProductBrandService;
import cn.cleanarch.dmp.module.product.service.category.ProductCategoryService;
import cn.cleanarch.dmp.module.product.service.property.ProductPropertyService;
import cn.cleanarch.dmp.module.product.service.property.ProductPropertyValueService;
import cn.cleanarch.dmp.module.product.service.sku.ProductSkuService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.cleanarch.dmp.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.cleanarch.dmp.module.product.enums.ErrorCodeConstants.SPU_NOT_EXISTS;

/**
 * ?????? SPU Service ?????????
 *
 * @author ????????????
 */
@Service
@Validated
public class ProductSpuServiceImpl implements ProductSpuService {

    @Resource
    private ProductSpuMapper productSpuMapper;

    @Resource
    private ProductCategoryService categoryService;

    @Resource
    @Lazy // ???????????????????????????
    private ProductSkuService productSkuService;
    @Resource
    private ProductPropertyService productPropertyService;
    @Resource
    private ProductPropertyValueService productPropertyValueService;
    @Resource
    private ProductBrandService brandService;

    @Override
    @Transactional
    public Long createSpu(ProductSpuCreateReqVO createReqVO) {
        // ????????????
        categoryService.validateCategoryLevel(createReqVO.getCategoryId());
        // ????????????
        brandService.validateProductBrand(createReqVO.getBrandId());
        // ??????SKU
        List<ProductSkuCreateOrUpdateReqVO> skuCreateReqList = createReqVO.getSkus();
        productSkuService.validateSkus(skuCreateReqList, createReqVO.getSpecType());
        // ?????? SPU
        ProductSpuDO spu = ProductSpuConvert.INSTANCE.convert(createReqVO);
        spu.setMarketPrice(CollectionUtils.getMaxValue(skuCreateReqList, ProductSkuCreateOrUpdateReqVO::getMarketPrice));
        spu.setMaxPrice(CollectionUtils.getMaxValue(skuCreateReqList, ProductSkuCreateOrUpdateReqVO::getPrice));
        spu.setMinPrice(CollectionUtils.getMinValue(skuCreateReqList, ProductSkuCreateOrUpdateReqVO::getPrice));
        spu.setTotalStock(CollectionUtils.getSumValue(skuCreateReqList, ProductSkuCreateOrUpdateReqVO::getStock, Integer::sum));
        productSpuMapper.insert(spu);
        // ?????? SKU
        productSkuService.createSkus(spu.getId(), skuCreateReqList);
        // ??????
        return spu.getId();
    }

    @Override
    @Transactional
    public void updateSpu(ProductSpuUpdateReqVO updateReqVO) {
        // ?????? SPU ????????????
        validateSpuExists(updateReqVO.getId());
        // ????????????
        categoryService.validateCategoryLevel(updateReqVO.getCategoryId());
        // ????????????
        brandService.validateProductBrand(updateReqVO.getBrandId());
        // ??????SKU
        List<ProductSkuCreateOrUpdateReqVO> skuCreateReqList = updateReqVO.getSkus();
        productSkuService.validateSkus(skuCreateReqList, updateReqVO.getSpecType());

        // ?????? SPU
        ProductSpuDO updateObj = ProductSpuConvert.INSTANCE.convert(updateReqVO);
        updateObj.setMarketPrice(CollectionUtils.getMaxValue(skuCreateReqList, ProductSkuCreateOrUpdateReqVO::getMarketPrice));
        updateObj.setMaxPrice(CollectionUtils.getMaxValue(skuCreateReqList, ProductSkuCreateOrUpdateReqVO::getPrice));
        updateObj.setMinPrice(CollectionUtils.getMinValue(skuCreateReqList, ProductSkuCreateOrUpdateReqVO::getPrice));
        updateObj.setTotalStock(CollectionUtils.getSumValue(skuCreateReqList, ProductSkuCreateOrUpdateReqVO::getStock, Integer::sum));
        productSpuMapper.updateById(updateObj);
        // ???????????? SKU
        productSkuService.updateSkus(updateObj.getId(), updateReqVO.getSkus());
    }

    @Override
    @Transactional
    public void deleteSpu(Long id) {
        // ????????????
        validateSpuExists(id);
        // ?????? SPU
        productSpuMapper.deleteById(id);
        // ??????????????? SKU
        productSkuService.deleteSkuBySpuId(id);
    }

    private void validateSpuExists(Long id) {
        if (productSpuMapper.selectById(id) == null) {
            throw exception(SPU_NOT_EXISTS);
        }
    }

    @Override
    // TODO @?????????????????? review ???
    public ProductSpuDetailRespVO getSpuDetail(Long id) {
        ProductSpuDO spu = productSpuMapper.selectById(id);
        ProductSpuDetailRespVO respVO = BeanUtil.copyProperties(spu, ProductSpuDetailRespVO.class);
        if (null != spu) {
            List<ProductSpuDetailRespVO.Sku> skuReqs = ProductSkuConvert.INSTANCE.convertList03(productSkuService.getSkusBySpuId(id));
            respVO.setSkus(skuReqs);
            // ?????? sku ????????????
            if (spu.getSpecType().equals(ProductSpuSpecTypeEnum.DISABLE.getType())) {
                List<ProductSkuRespVO.Property> properties = new ArrayList<>();
                for (ProductSpuDetailRespVO.Sku productSkuRespVO : skuReqs) {
                    properties.addAll(productSkuRespVO.getProperties());
                }
                Map<Long, List<ProductSkuBaseVO.Property>> propertyMaps = properties.stream().collect(Collectors.groupingBy(ProductSkuBaseVO.Property::getPropertyId));

                List<ProductPropertyValueRespVO> propertyValueList = productPropertyValueService.getPropertyValueListByPropertyId(new ArrayList<>(propertyMaps.keySet()));
                List<ProductPropertyRespVO> propertyList = productPropertyService.getPropertyList(new ArrayList<>(propertyMaps.keySet()));
                // ???????????????????????????
                List<ProductPropertyViewRespVO> productPropertyViews = new ArrayList<>();
                propertyList.forEach(p -> {
                    ProductPropertyViewRespVO productPropertyViewRespVO = new ProductPropertyViewRespVO();
                    productPropertyViewRespVO.setPropertyId(p.getId());
                    productPropertyViewRespVO.setName(p.getName());
                    List<ProductPropertyViewRespVO.Tuple2> propertyValues = new ArrayList<>();
                    // ?????????map????????????????????????
                    Map<Long, ProductPropertyValueRespVO> propertyValueMaps = CollectionUtils.convertMap(propertyValueList, ProductPropertyValueRespVO::getId);
                    propertyMaps.get(p.getId()).forEach(pv -> {
                        ProductPropertyViewRespVO.Tuple2 tuple2 = new ProductPropertyViewRespVO.Tuple2(pv.getValueId(), propertyValueMaps.get(pv.getValueId()).getName());
                        propertyValues.add(tuple2);
                    });
                    productPropertyViewRespVO.setPropertyValues(propertyValues.stream().distinct().collect(Collectors.toList()));
                    productPropertyViews.add(productPropertyViewRespVO);
                });
                respVO.setProductPropertyViews(productPropertyViews);
            }
        }
        return respVO;
    }

    @Override
    public ProductSpuRespVO getSpu(Long id) {
        return ProductSpuConvert.INSTANCE.convert(productSpuMapper.selectById(id));
    }

    @Override
    public List<ProductSpuDO> getSpuList(Collection<Long> ids) {
        return productSpuMapper.selectBatchIds(ids);
    }

    @Override
    public List<ProductSpuDO> getSpuList() {
        return productSpuMapper.selectList();
    }

    @Override
    public PageResult<ProductSpuRespVO> getSpuPage(ProductSpuPageReqVO pageReqVO) {
        // ??????????????? SPU ???????????????
        Set<Long> alarmStockSpuIds = null;
        if (Boolean.TRUE.equals(pageReqVO.getAlarmStock())) {
            alarmStockSpuIds = CollectionUtils.convertSet(productSkuService.getSkusByAlarmStock(), ProductSkuDO::getSpuId);
            if (CollUtil.isEmpty(alarmStockSpuIds)) {
                return PageResult.empty();
            }
        }
        // ????????????
        return ProductSpuConvert.INSTANCE.convertPage(productSpuMapper.selectPage(pageReqVO, alarmStockSpuIds));
    }

    @Override
    public PageResult<AppSpuPageRespVO> getSpuPage(AppSpuPageReqVO pageReqVO) {
        // TODO ?????????????????????????????????
        PageResult<ProductSpuDO> productSpuDOPageResult = productSpuMapper.selectPage(ProductSpuConvert.INSTANCE.convert(pageReqVO));
        PageResult<AppSpuPageRespVO> pageResult = new PageResult<>();
        // TODO @?????? ?????????convert????????????
        List<AppSpuPageRespVO> collect = productSpuDOPageResult.getList()
                .stream()
                .map(ProductSpuConvert.INSTANCE::convertAppResp)
                .collect(Collectors.toList());
        pageResult.setList(collect);
        pageResult.setTotal(productSpuDOPageResult.getTotal());
        return pageResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpuStock(Map<Long, Integer> stockIncrCounts) {
        stockIncrCounts.forEach((id, incCount) -> productSpuMapper.updateStock(id, incCount));
    }

}
