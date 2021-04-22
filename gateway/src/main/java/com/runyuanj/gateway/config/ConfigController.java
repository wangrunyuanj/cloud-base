package com.runyuanj.gateway.config;

import com.runyuanj.common.response.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试自动配置
 */
@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

//    @Resource(name= Source.OUTPUT)
//    private MessageChannel messageChannel;


    @RequestMapping("/get")
    public boolean get() {
        return useLocalCache;
    }

    @PostMapping("/add")
    public Result addConfig(@RequestBody RouteDefinition routeDefinition) {
//        boolean send = messageChannel.send(MessageBuilder.withPayload(routeDefinition).build());
//        if (send) {
//            return Result.success();
//        } else {
//            return Result.fail();
//        }
        return Result.success();
    }

}