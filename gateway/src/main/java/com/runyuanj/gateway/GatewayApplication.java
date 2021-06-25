package com.runyuanj.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.runyuanj.auth.api","com.runyuanj.gateway"})
@EnableCaching
@EnableFeignClients(basePackages = "com.runyuanj.auth.api")
// @Import({HttpSecurityConfig.class, OAuth2Configuration.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
