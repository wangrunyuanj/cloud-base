package com.runyuanj.authorization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Import(com.runyuanj.core.config.CoreAutoConfiguration.class)
public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        // 异常信息本地化 Spring Security 的 messages_zh_CN.properties
        messageSource.setBasename("messages_zh_CN");
        return messageSource;
    }

}
