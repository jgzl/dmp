package cn.cleanarch.dmp.module.bpm.convert.definition;

import cn.cleanarch.dmp.module.bpm.controller.admin.definition.vo.form.BpmFormCreateReqVO;
import cn.cleanarch.dmp.module.bpm.controller.admin.definition.vo.form.BpmFormRespVO;
import cn.cleanarch.dmp.module.bpm.controller.admin.definition.vo.form.BpmFormSimpleRespVO;
import cn.cleanarch.dmp.module.bpm.controller.admin.definition.vo.form.BpmFormUpdateReqVO;
import cn.cleanarch.dmp.module.bpm.dal.dataobject.definition.BpmFormDO;
import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 动态表单 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface BpmFormConvert {

    BpmFormConvert INSTANCE = Mappers.getMapper(BpmFormConvert.class);

    BpmFormDO convert(BpmFormCreateReqVO bean);

    BpmFormDO convert(BpmFormUpdateReqVO bean);

    BpmFormRespVO convert(BpmFormDO bean);

    List<BpmFormSimpleRespVO> convertList2(List<BpmFormDO> list);

    PageResult<BpmFormRespVO> convertPage(PageResult<BpmFormDO> page);

}
