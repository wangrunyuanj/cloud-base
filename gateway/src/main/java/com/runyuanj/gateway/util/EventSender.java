package com.runyuanj.gateway.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Slf4j
@AllArgsConstructor
public class EventSender {

    private RabbitTemplate rabbitTemplate;

    private String topic;

    public void send(String key, String msg) {
        rabbitTemplate.convertAndSend(topic, key, msg);
    }

}
