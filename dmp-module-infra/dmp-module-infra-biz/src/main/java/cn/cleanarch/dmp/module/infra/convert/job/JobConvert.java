package cn.cleanarch.dmp.module.infra.convert.job;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.module.infra.controller.admin.job.vo.job.JobCreateReqVO;
import cn.cleanarch.dmp.module.infra.controller.admin.job.vo.job.JobExcelVO;
import cn.cleanarch.dmp.module.infra.controller.admin.job.vo.job.JobRespVO;
import cn.cleanarch.dmp.module.infra.controller.admin.job.vo.job.JobUpdateReqVO;
import cn.cleanarch.dmp.module.infra.dal.dataobject.job.JobDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface JobConvert {

    JobConvert INSTANCE = Mappers.getMapper(JobConvert.class);

    JobDO convert(JobCreateReqVO bean);

    JobDO convert(JobUpdateReqVO bean);

    JobRespVO convert(JobDO bean);

    List<JobRespVO> convertList(List<JobDO> list);

    PageResult<JobRespVO> convertPage(PageResult<JobDO> page);

    List<JobExcelVO> convertList02(List<JobDO> list);

}
