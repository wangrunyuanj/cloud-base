package com.runyuanj.org.config;

import com.runyuanj.cloud.cache.redis.config.RedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 */
@Configuration
@EnableCaching
public class MyRedisConfig extends RedisConfig {

}
