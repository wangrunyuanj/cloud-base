package com.runyuanj.gateway;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.runyuanj.authorization.config.HttpSecurityConfig;
import com.runyuanj.authorization.config.OAuth2Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
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
    public ApplicationRunner runner(RabbitTemplate template) {
        return args -> {
            System.out.println("------------------------------------------------");
            template.convertAndSend("process-in-0.someGroup", "第一条消息");
            template.convertAndSend("process-in-0.someGroup", "第二条消息");
        };
    }

    @Bean
    public Function<String, String> output() {
        return s -> s.toUpperCase();
    }


    @Bean
    public Function<String, String> uppercaseFunction(RabbitTemplate template) {
        return s -> {
            System.out.println("-----upercase: " + s);
            template.convertAndSend("process-in-0", s.toUpperCase());
            return s.toUpperCase();
        };
    }

    @Bean
    Consumer<List<String>> input() {
        return list -> {
            list.forEach(s -> System.out.println("received message: " + s + "\n"));
        };
    }

    @Bean
    public Consumer<String> process() {
        return  s -> System.out.println("---------------received: " + s);
    }


    @Bean
    public Consumer<List<String>> gateway() {
        return list -> {
            System.out.println("---------------received: " + list.size());
            list.forEach(s -> System.out.println("received message: " + s + "\n"));
        };
    }
}
