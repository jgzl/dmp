package cn.cleanarch.dmp.module.system.convert.sms;

import cn.cleanarch.dmp.module.system.controller.admin.sms.vo.template.SmsTemplateCreateReqVO;
import cn.cleanarch.dmp.module.system.controller.admin.sms.vo.template.SmsTemplateExcelVO;
import cn.cleanarch.dmp.module.system.controller.admin.sms.vo.template.SmsTemplateRespVO;
import cn.cleanarch.dmp.module.system.controller.admin.sms.vo.template.SmsTemplateUpdateReqVO;
import cn.cleanarch.dmp.module.system.dal.dataobject.sms.SmsTemplateDO;
import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SmsTemplateConvert {

    SmsTemplateConvert INSTANCE = Mappers.getMapper(SmsTemplateConvert.class);

    SmsTemplateDO convert(SmsTemplateCreateReqVO bean);

    SmsTemplateDO convert(SmsTemplateUpdateReqVO bean);

    SmsTemplateRespVO convert(SmsTemplateDO bean);

    List<SmsTemplateRespVO> convertList(List<SmsTemplateDO> list);

    PageResult<SmsTemplateRespVO> convertPage(PageResult<SmsTemplateDO> page);

    List<SmsTemplateExcelVO> convertList02(List<SmsTemplateDO> list);

}
