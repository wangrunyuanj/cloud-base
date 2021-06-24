package com.runyuanj.gateway.config;

import com.runyuanj.gateway.util.EventSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class EventConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Consumer<String> route() {
        return message -> {
            log.info("route接收的普通消息为：{}", message);
        };
    }

    @Bean
    public EventSender eventService() {
        return new EventSender(rabbitTemplate, "route-in-0");
    }
}
