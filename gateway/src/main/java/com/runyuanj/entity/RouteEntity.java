package com.runyuanj.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daixueyun on 2019/8/22 0022.
 *
 * 路由实体类
 */
@Data
public class RouteEntity {

    //路由id
    private String id;

    //路由断言集合
    private List<PredicateEntity> predicates = new ArrayList<>();

    //路由过滤器集合
    private List<FilterEntity> filters = new ArrayList<>();

    //路由转发的目标uri
    private String uri;

    //路由执行的顺序
    private int order = 0;

}