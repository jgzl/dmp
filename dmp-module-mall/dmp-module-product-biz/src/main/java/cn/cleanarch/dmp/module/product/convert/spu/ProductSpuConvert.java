package cn.cleanarch.dmp.module.product.convert.spu;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.cleanarch.dmp.module.product.controller.admin.spu.vo.*;
import cn.cleanarch.dmp.module.product.controller.app.spu.vo.AppSpuPageReqVO;
import cn.cleanarch.dmp.module.product.controller.app.spu.vo.AppSpuPageRespVO;
import cn.cleanarch.dmp.module.product.dal.dataobject.spu.ProductSpuDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 商品spu Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductSpuConvert {

    ProductSpuConvert INSTANCE = Mappers.getMapper(ProductSpuConvert.class);

    ProductSpuDO convert(ProductSpuCreateReqVO bean);

    ProductSpuDO convert(ProductSpuUpdateReqVO bean);

    ProductSpuRespVO convert(ProductSpuDO bean);

    List<ProductSpuRespVO> convertList(List<ProductSpuDO> list);

    PageResult<ProductSpuRespVO> convertPage(PageResult<ProductSpuDO> page);

    ProductSpuPageReqVO convert(AppSpuPageReqVO bean);

    AppSpuPageRespVO convertAppResp(ProductSpuDO list);

    List<ProductSpuRespDTO> convertList2(List<ProductSpuDO> list);

    List<ProductSpuSimpleRespVO> convertList02(List<ProductSpuDO> list);

}
