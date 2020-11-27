package com.runyuanj.entity;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by daixueyun on 2019/8/22 0022.
 *
 * 过滤器实体类
 */
@Data
public class FilterEntity {

    //过滤器对应的Name
    private String name;

    //路由规则
    private Map<String, String> args = new LinkedHashMap<>();
}