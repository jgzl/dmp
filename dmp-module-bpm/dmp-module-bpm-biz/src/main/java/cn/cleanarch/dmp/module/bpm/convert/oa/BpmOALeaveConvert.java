package cn.cleanarch.dmp.module.bpm.convert.oa;

import cn.cleanarch.dmp.module.bpm.controller.admin.oa.vo.BpmOALeaveCreateReqVO;
import cn.cleanarch.dmp.module.bpm.controller.admin.oa.vo.BpmOALeaveRespVO;
import cn.cleanarch.dmp.module.bpm.dal.dataobject.oa.BpmOALeaveDO;
import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 请假申请 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface BpmOALeaveConvert {

    BpmOALeaveConvert INSTANCE = Mappers.getMapper(BpmOALeaveConvert.class);

    BpmOALeaveDO convert(BpmOALeaveCreateReqVO bean);

    BpmOALeaveRespVO convert(BpmOALeaveDO bean);

    List<BpmOALeaveRespVO> convertList(List<BpmOALeaveDO> list);

    PageResult<BpmOALeaveRespVO> convertPage(PageResult<BpmOALeaveDO> page);

}
