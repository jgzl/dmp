package cn.cleanarch.dmp.framework.idempotent.config;

import cn.cleanarch.dmp.framework.idempotent.core.aop.IdempotentAspect;
import cn.cleanarch.dmp.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import cn.cleanarch.dmp.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import cn.cleanarch.dmp.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import cn.cleanarch.dmp.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import cn.cleanarch.dmp.framework.redis.config.DmpRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = DmpRedisAutoConfiguration.class)
public class DmpIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
