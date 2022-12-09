package cn.cleanarch.dmp.module.product.convert.brand;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.module.product.controller.admin.brand.vo.ProductBrandCreateReqVO;
import cn.cleanarch.dmp.module.product.controller.admin.brand.vo.ProductBrandRespVO;
import cn.cleanarch.dmp.module.product.controller.admin.brand.vo.ProductBrandUpdateReqVO;
import cn.cleanarch.dmp.module.product.dal.dataobject.brand.ProductBrandDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 品牌 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductBrandConvert {

    ProductBrandConvert INSTANCE = Mappers.getMapper(ProductBrandConvert.class);

    ProductBrandDO convert(ProductBrandCreateReqVO bean);

    ProductBrandDO convert(ProductBrandUpdateReqVO bean);

    ProductBrandRespVO convert(ProductBrandDO bean);

    List<ProductBrandRespVO> convertList(List<ProductBrandDO> list);

    PageResult<ProductBrandRespVO> convertPage(PageResult<ProductBrandDO> page);

}
