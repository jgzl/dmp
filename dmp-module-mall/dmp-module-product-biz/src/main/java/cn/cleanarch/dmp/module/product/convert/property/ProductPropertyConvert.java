package cn.cleanarch.dmp.module.product.convert.property;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.property.ProductPropertyAndValueRespVO;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.property.ProductPropertyCreateReqVO;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.property.ProductPropertyRespVO;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.property.ProductPropertyUpdateReqVO;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.value.ProductPropertyValueRespVO;
import cn.cleanarch.dmp.module.product.dal.dataobject.property.ProductPropertyDO;
import cn.cleanarch.dmp.module.product.dal.dataobject.property.ProductPropertyValueDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 规格名称 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductPropertyConvert {

    ProductPropertyConvert INSTANCE = Mappers.getMapper(ProductPropertyConvert.class);

    ProductPropertyDO convert(ProductPropertyCreateReqVO bean);

    ProductPropertyDO convert(ProductPropertyUpdateReqVO bean);

    ProductPropertyAndValueRespVO convert(ProductPropertyRespVO bean);

    ProductPropertyRespVO convert(ProductPropertyDO bean);

    List<ProductPropertyRespVO> convertList(List<ProductPropertyDO> list);

    PageResult<ProductPropertyRespVO> convertPage(PageResult<ProductPropertyDO> page);

}
