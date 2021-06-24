package com.runyuanj.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.runyuanj.common.response.Result;
import com.runyuanj.gateway.entity.po.GatewayRoute;
import com.runyuanj.gateway.service.IRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 管理网关过滤器
 *
 * @author runyu
 */
@Slf4j
@RequestMapping("/filter")
@RestController
public class GatewayFilterController {

    @Value("${runyu.web.name:''}")
    private String name;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IRouteService routeService;

    @GetMapping("/test")
    public Result addRoute() {
        System.out.println("gateway-config-web " + name);
        GatewayRoute gatewayRoute = new GatewayRoute();
        gatewayRoute.setId("1");
        gatewayRoute.setPredicates("[{\"name\":\"Path\",\"args\":{\"pattern\":\"/gateway-admin/**\"}}]");
        gatewayRoute.setRouteId("testRoute");
        gatewayRoute.setUri("lb://authorization-server:8003");
        gatewayRoute.setCreatedBy("runyu");
        gatewayRoute.setCreatedTime(new Date());
        gatewayRoute.setUpdatedBy("runyu");
        gatewayRoute.setFilters("[{\"name\":\"StripPrefix\",\"args\":{\"parts\":\"1\"}}]");
        gatewayRoute.setOrders(100);
        gatewayRoute.setDescription("test");
        gatewayRoute.setStatus("Y");
        gatewayRoute.setUpdatedTime(new Date());
        boolean save = routeService.add(gatewayRoute);
        return Result.success(save);
    }


    @GetMapping("/{name}")
    public Result test(@PathVariable(value = "name") String name) {
        return Result.fail();
    }

}
