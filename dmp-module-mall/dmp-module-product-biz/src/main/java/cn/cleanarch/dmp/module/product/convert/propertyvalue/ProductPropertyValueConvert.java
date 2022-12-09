package cn.cleanarch.dmp.module.product.convert.propertyvalue;

import java.util.*;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;

import cn.cleanarch.dmp.module.product.controller.admin.property.vo.value.ProductPropertyValueCreateReqVO;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.value.ProductPropertyValueRespVO;
import cn.cleanarch.dmp.module.product.controller.admin.property.vo.value.ProductPropertyValueUpdateReqVO;
import cn.cleanarch.dmp.module.product.dal.dataobject.property.ProductPropertyValueDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 规格值 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductPropertyValueConvert {

    ProductPropertyValueConvert INSTANCE = Mappers.getMapper(ProductPropertyValueConvert.class);

    ProductPropertyValueDO convert(ProductPropertyValueCreateReqVO bean);

    ProductPropertyValueDO convert(ProductPropertyValueUpdateReqVO bean);

    ProductPropertyValueRespVO convert(ProductPropertyValueDO bean);

    List<ProductPropertyValueRespVO> convertList(List<ProductPropertyValueDO> list);

    PageResult<ProductPropertyValueRespVO> convertPage(PageResult<ProductPropertyValueDO> page);

    List<ProductPropertyValueDO> convertList03(List<ProductPropertyValueCreateReqVO> list);

}
