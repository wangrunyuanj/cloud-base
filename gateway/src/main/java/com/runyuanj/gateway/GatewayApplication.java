package com.runyuanj.gateway;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.runyuanj.authorization.config.HttpSecurityConfig;
import com.runyuanj.authorization.config.OAuth2Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Slf4j
@EnableBinding(Sink.class)
@EnableMethodCache(basePackages = "com.runyuanj.gateway")
@EnableDiscoveryClient
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableFeignClients

@Import({HttpSecurityConfig.class, OAuth2Configuration.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 异常信息本地化 Spring Security 的 messages_zh_CN.properties
        messageSource.setBasename("messages_zh_CN");
        return messageSource;
    }

}
