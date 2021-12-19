package com.example.specialserver.config;

import com.example.specialserver.properties.RedissonProperties;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(value = RedissonProperties.class)
@ConditionalOnClass(RedissonProperties.class)
public class RedissonConfiguration {

    @Resource
    private RedissonProperties redissonProperties;


    /**
     * 注入config
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(Config.class)
    public Config config(){
        Config config = new Config();
        try {
            config.setCodec((Codec) Class.forName(redissonProperties.getCodec()).newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        config.setReferenceEnabled(redissonProperties.getReferenceEnabled());
        config.setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout());
        config.setKeepPubSubOrder(redissonProperties.getKeepPubSubOrder());
        config.setDecodeInExecutor(redissonProperties.getDecodeInExecutor());
        config.setUseScriptCache(redissonProperties.getUseScriptCache());
        config.setMinCleanUpDelay(redissonProperties.getMinCleanUpDelay());
        config.setMaxCleanUpDelay(redissonProperties.getMaxCleanUpDelay());
        config.setTransportMode((redissonProperties.getTransportMode() == null)? TransportMode.NIO : redissonProperties.getTransportMode());
        return config;
    }

}
