package com.runyuanj.gateway.service;

import com.runyuanj.gateway.entity.po.GatewayRoute;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.Collection;

public interface IRouteService {

    /**
     * 加载路由
     */
    void reload();

    boolean add(GatewayRoute gatewayRoute);

    Collection<RouteDefinition> getRouteDefinitions();

    /**
     * 更新缓存
     *
     * @param routeDefinition
     * @return
     */
    boolean addRouteDefinition(RouteDefinition routeDefinition);

    /**
     * 删除路由
     *
     * @param routeId
     * @return
     */
    boolean delete(String routeId);
}
