package com.runyuanj.gateway;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.runyuanj.authorization.config.HttpSecurityConfig;
import com.runyuanj.authorization.config.JwtAuthenticateConfigurer;
import com.runyuanj.authorization.config.OAuth2Configuration;
import com.runyuanj.authorization.config.ResourcePermissionConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@EnableMethodCache(basePackages = "com.runyuanj.gateway")
@EnableDiscoveryClient
@SpringBootApplication
@EnableCreateCacheAnnotation
@EnableFeignClients
@Import({HttpSecurityConfig.class, JwtAuthenticateConfigurer.class, OAuth2Configuration.class, ResourcePermissionConfigurer.class})
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
