package com.runyuanj.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.support.DefaultServerCodecConfigurer;

/**
 * 在代码中设置路由策略
 * 自定义路由策略需要实现GlobalFilter
 */
// @Configuration
public class GatewayConfiguration {

    // @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return new DefaultServerCodecConfigurer();
    }

    /**
     * 使用方法配置路由
     *
     * @param builder
     * @return
     */
    // @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/login.html")
                        .uri("localhost:8001/login"))
                .build();
    }

}
