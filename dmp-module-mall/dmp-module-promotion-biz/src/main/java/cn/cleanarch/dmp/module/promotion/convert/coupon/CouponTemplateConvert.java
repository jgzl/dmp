package cn.cleanarch.dmp.module.promotion.convert.coupon;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.module.promotion.controller.admin.coupon.vo.template.CouponTemplateCreateReqVO;
import cn.cleanarch.dmp.module.promotion.controller.admin.coupon.vo.template.CouponTemplateRespVO;
import cn.cleanarch.dmp.module.promotion.controller.admin.coupon.vo.template.CouponTemplateUpdateReqVO;
import cn.cleanarch.dmp.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 优惠劵模板 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface CouponTemplateConvert {

    CouponTemplateConvert INSTANCE = Mappers.getMapper(CouponTemplateConvert.class);

    CouponTemplateDO convert(CouponTemplateCreateReqVO bean);

    CouponTemplateDO convert(CouponTemplateUpdateReqVO bean);

    CouponTemplateRespVO convert(CouponTemplateDO bean);

    PageResult<CouponTemplateRespVO> convertPage(PageResult<CouponTemplateDO> page);

}
