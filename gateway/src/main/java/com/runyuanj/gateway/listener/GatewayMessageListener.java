package com.runyuanj.gateway.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GatewayMessageListener {

//    @StreamListener(Sink.INPUT)
//    public void receive(Message<String> message) {
//        // RouteDefinition payload = message.getPayload();
//        log.info("-------------received message: {}", message.getPayload());
//        // routeService.save(payload);
//    }
}
