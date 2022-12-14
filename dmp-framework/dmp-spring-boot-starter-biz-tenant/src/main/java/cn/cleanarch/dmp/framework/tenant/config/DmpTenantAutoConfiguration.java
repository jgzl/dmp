package cn.cleanarch.dmp.framework.tenant.config;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.cleanarch.dmp.framework.common.enums.WebFilterOrderEnum;
import cn.cleanarch.dmp.framework.mybatis.core.util.MyBatisUtils;
import cn.cleanarch.dmp.framework.quartz.core.handler.JobHandler;
import cn.cleanarch.dmp.framework.tenant.core.aop.TenantIgnoreAspect;
import cn.cleanarch.dmp.framework.tenant.core.db.TenantDatabaseInterceptor;
import cn.cleanarch.dmp.framework.tenant.core.job.TenantJob;
import cn.cleanarch.dmp.framework.tenant.core.job.TenantJobHandlerDecorator;
import cn.cleanarch.dmp.framework.tenant.core.mq.TenantRedisMessageInterceptor;
import cn.cleanarch.dmp.framework.tenant.core.redis.TenantRedisCacheManager;
import cn.cleanarch.dmp.framework.tenant.core.security.TenantSecurityWebFilter;
import cn.cleanarch.dmp.framework.tenant.core.service.TenantFrameworkService;
import cn.cleanarch.dmp.framework.tenant.core.service.TenantFrameworkServiceImpl;
import cn.cleanarch.dmp.framework.tenant.core.web.TenantContextWebFilter;
import cn.cleanarch.dmp.framework.web.config.WebProperties;
import cn.cleanarch.dmp.framework.web.core.handler.GlobalExceptionHandler;
import cn.cleanarch.dmp.module.system.api.tenant.TenantApi;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Objects;

@AutoConfiguration
@ConditionalOnProperty(prefix = "dmp.tenant", value = "enable", matchIfMissing = true) // ???????????? dmp.tenant.enable=false ???????????????
@EnableConfigurationProperties(TenantProperties.class)
public class DmpTenantAutoConfiguration {

    @Bean
    public TenantFrameworkService tenantFrameworkService(TenantApi tenantApi) {
        return new TenantFrameworkServiceImpl(tenantApi);
    }

    // ========== AOP ==========

    @Bean
    public TenantIgnoreAspect tenantIgnoreAspect() {
        return new TenantIgnoreAspect();
    }

    // ========== DB ==========

    @Bean
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(TenantProperties properties,
                                                                 MybatisPlusInterceptor interceptor) {
        TenantLineInnerInterceptor inner = new TenantLineInnerInterceptor(new TenantDatabaseInterceptor(properties));
        // ????????? interceptor ???
        // ????????????????????????????????????????????????????????????????????? MyBatis Plus ?????????
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    // ========== WEB ==========

    @Bean
    public FilterRegistrationBean<TenantContextWebFilter> tenantContextWebFilter() {
        FilterRegistrationBean<TenantContextWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantContextWebFilter());
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_CONTEXT_FILTER);
        return registrationBean;
    }

    // ========== Security ==========

    @Bean
    public FilterRegistrationBean<TenantSecurityWebFilter> tenantSecurityWebFilter(TenantProperties tenantProperties,
                                                                                   WebProperties webProperties,
                                                                                   GlobalExceptionHandler globalExceptionHandler,
                                                                                   TenantFrameworkService tenantFrameworkService) {
        FilterRegistrationBean<TenantSecurityWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantSecurityWebFilter(tenantProperties, webProperties,
                globalExceptionHandler, tenantFrameworkService));
        registrationBean.setOrder(WebFilterOrderEnum.TENANT_SECURITY_FILTER);
        return registrationBean;
    }

    // ========== MQ ==========

    @Bean
    public TenantRedisMessageInterceptor tenantRedisMessageInterceptor() {
        return new TenantRedisMessageInterceptor();
    }

    // ========== Job ==========

    @Bean
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public BeanPostProcessor jobHandlerBeanPostProcessor(TenantFrameworkService tenantFrameworkService) {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (!(bean instanceof JobHandler)) {
                    return bean;
                }
                // ??? TenantJob ???????????????????????????????????????
                if (!AnnotationUtil.hasAnnotation(bean.getClass(), TenantJob.class)) {
                    return bean;
                }

                // ?????? TenantJobHandlerDecorator ??????
                return new TenantJobHandlerDecorator(tenantFrameworkService, (JobHandler) bean);
            }

        };
    }

    // ========== Redis ==========

    @Bean
    @Primary // ??????????????????tenantRedisCacheManager ?????? Bean
    public RedisCacheManager tenantRedisCacheManager(RedisTemplate<String, Object> redisTemplate,
                                                     RedisCacheConfiguration redisCacheConfiguration) {
        // ?????? RedisCacheWriter ??????
        RedisConnectionFactory connectionFactory = Objects.requireNonNull(redisTemplate.getConnectionFactory());
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        // ?????? TenantRedisCacheManager ??????
        return new TenantRedisCacheManager(cacheWriter, redisCacheConfiguration);
    }

}
