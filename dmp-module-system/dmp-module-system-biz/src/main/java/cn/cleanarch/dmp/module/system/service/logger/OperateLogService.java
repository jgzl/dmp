package cn.cleanarch.dmp.module.system.service.logger;

import cn.cleanarch.dmp.framework.common.pojo.PageResult;
import cn.cleanarch.dmp.framework.operatelog.core.service.OperateLog;
import cn.cleanarch.dmp.framework.operatelog.core.service.OperateLogFrameworkService;
import cn.cleanarch.dmp.module.system.api.logger.dto.OperateLogCreateReqDTO;
import cn.cleanarch.dmp.module.system.controller.admin.logger.vo.operatelog.OperateLogExportReqVO;
import cn.cleanarch.dmp.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import cn.cleanarch.dmp.module.system.dal.dataobject.logger.OperateLogDO;

import java.util.List;

/**
 * 操作日志 Service 接口
 *
 * @author 芋道源码
 */
public interface OperateLogService {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 操作日志请求
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param reqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO reqVO);

    /**
     * 获得操作日志列表
     *
     * @param reqVO 列表条件
     * @return 日志列表
     */
    List<OperateLogDO> getOperateLogs(OperateLogExportReqVO reqVO);

}
