package com.runyuanj.gateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.runyuanj.gateway.entity.po.GatewayRoute;
import com.runyuanj.gateway.mapper.GatewayRouteMapper;
import com.runyuanj.gateway.service.IRouteService;
import com.runyuanj.gateway.config.EventSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;
import org.springframework.util.ConcurrentReferenceHashMap;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 网关路由的增删改查
 * <p>
 * 加载路由. 存入缓存
 */
@Service
@Slf4j
public class RouteService extends ServiceImpl<GatewayRouteMapper, GatewayRoute> implements IRouteService {

    @Autowired
    private EventSender eventSender;

    /**
     * routeDefinition缓存, 弱引用
     * TODO 添加redis
     */
    private Map<String, RouteDefinition> gatewayRouteCache = new ConcurrentReferenceHashMap<>(128);

    @Override
    @PostConstruct
    public void reload() {
        log.info("加载路由");
        List<GatewayRoute> gatewayRoutes = this.list(new QueryWrapper<>());
        gatewayRoutes.forEach(gatewayRoute ->
                gatewayRouteCache.put(gatewayRoute.getRouteId(), gatewayRouteToRouteDefinition(gatewayRoute)));
    }

    @Override
    public boolean add(GatewayRoute gatewayRoute) {

        boolean isSuccess = this.save(gatewayRoute);

        RouteDefinition routeDefinition = gatewayRouteToRouteDefinition(gatewayRoute);
        addRouteDefinition(routeDefinition);

        log.info("新增路由规则：{}", routeDefinition);
        return isSuccess;
    }

    @Override
    public boolean delete(String routeId) {
        boolean isSuccess = this.removeById(routeId);
        log.info("删除路由1条：{},目前路由共{}条", routeId, gatewayRouteCache.size());

        gatewayRouteCache.remove(routeId);
        eventSender.send("rm-route", routeId);

        return isSuccess;
    }

    @Override
    public Collection<RouteDefinition> getRouteDefinitions() {
        if (gatewayRouteCache == null || gatewayRouteCache.isEmpty()) {
            reload();
        }
        return gatewayRouteCache.values();
    }

    /**
     * 向缓存中添加routeDefinition并将事件发送到消息队列
     *
     * @param routeDefinition
     * @return
     */
    @Override
    public boolean addRouteDefinition(RouteDefinition routeDefinition) {
        gatewayRouteCache.put(routeDefinition.getId(), routeDefinition);
        eventSender.send("add-route", JSON.toJSONString(routeDefinition));
        return true;
    }

    /**
     * 将数据库中json对象转换为网关需要的RouteDefinition对象
     *
     * @param gatewayRoute
     * @return RouteDefinition
     */
    private RouteDefinition gatewayRouteToRouteDefinition(GatewayRoute gatewayRoute) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRoute.getRouteId());
        routeDefinition.setOrder(gatewayRoute.getOrders());
        routeDefinition.setUri(URI.create(gatewayRoute.getUri()));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            routeDefinition.setFilters(objectMapper.readValue(gatewayRoute.getFilters(), new TypeReference<List<FilterDefinition>>() {
            }));
            routeDefinition.setPredicates(objectMapper.readValue(gatewayRoute.getPredicates(), new TypeReference<List<PredicateDefinition>>() {
            }));
        } catch (IOException e) {
            log.error("网关路由对象转换失败", e);
        }
        return routeDefinition;
    }

}
