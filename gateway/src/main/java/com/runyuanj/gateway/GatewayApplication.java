package com.runyuanj.gateway;

import com.runyuanj.authorization.config.HttpSecurityConfig;
import com.runyuanj.authorization.config.OAuth2Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;


@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
@EnableFeignClients
@Import({HttpSecurityConfig.class, OAuth2Configuration.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
