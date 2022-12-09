package cn.cleanarch.dmp.framework.operatelog.config;

import cn.cleanarch.dmp.framework.operatelog.core.aop.OperateLogAspect;
import cn.cleanarch.dmp.framework.operatelog.core.service.OperateLogFrameworkService;
import cn.cleanarch.dmp.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import cn.cleanarch.dmp.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class DmpOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
