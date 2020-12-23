package com.runyuanj.authorization.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.runyuanj.authorization.service.OrgServiceFeign;
import com.runyuanj.authorization.service.ResourcePermissionService;
import com.runyuanj.common.response.Result;
import com.runyuanj.core.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.runyuanj.authorization.utils.Constants.NONE_URL;
import static java.util.stream.Collectors.toSet;

/**
 * 用于从org服务获取资源权限信息
 */
@Service
@Slf4j
public class ResourcePermissionServiceImpl implements ResourcePermissionService {

    @Autowired
    private OrgServiceFeign orgServiceFeign;

    /**
     * 系统中所有资源权限集合
     */
    private static final Map<RequestMatcher, ConfigAttribute> localConfigAttributes = new HashMap<>();

    @Override
    public void auth() {

    }

    /**
     * 动态新增更新权限
     *
     * @param resource
     */
    @Override
    public void saveResource(Resource resource) {
        localConfigAttributes.put(
                this.requestMatcher(resource.getUrl(), resource.getMethod()),
                new SecurityConfig(resource.getCode()));
    }

    /**
     * 动态删除权限
     *
     * @param resource
     */
    @Override
    public void removeResource(Resource resource) {
        localConfigAttributes.remove(this.requestMatcher(resource.getUrl(), resource.getMethod()));
    }

    /**
     * 加载权限资源数据
     */
    @Override
    public synchronized void loadResource() {
        Set<Resource> resources = getOrgResources();
        if (resources == null) {
            log.error("get org resources error");
            resources = new HashSet<>();
        }
        Map<RequestMatcher, SecurityConfig> temResources = resources.stream().collect(Collectors.toMap(
                resource -> this.requestMatcher(resource.getUrl(), resource.getMethod()),
                resource -> new SecurityConfig(resource.getCode())
        ));
        localConfigAttributes.putAll(temResources);
    }

    private Set<Resource> getOrgResources() {
        try {
            JSONObject responseEntity = orgServiceFeign.queryAll();
            if (Result.isJsonSuccess(responseEntity)) {
                JSONArray jsonArray = responseEntity.getJSONArray("data");
                return jsonArray.stream()
                        .map(obj -> JSON.parseObject(JSON.toJSONString(obj), Resource.class))
                        .collect(toSet());
            }
            return null;
        } catch (Exception e) {
            log.error("sed to org service for resources error");
            throw e;
        }
    }

    /**
     * 查询method访问对应对应的资源信息
     *
     * @param request
     * @return
     */
    @Override
    public ConfigAttribute findConfigAttributes(HttpServletRequest request) {
        if (localConfigAttributes.isEmpty()) {
            loadResource();
        }
        return localConfigAttributes.keySet().stream().filter(requestMatcher -> requestMatcher.matches(request))
                .map(requestMatcher -> localConfigAttributes.get(requestMatcher))
                .peek(urlConfig -> log.debug("url在资源池中配置：{}", urlConfig.getAttribute()))
                .findFirst()
                .orElse(new SecurityConfig(NONE_URL));
    }

    /**
     * 向org服务发送请求, 查询用户被授予的角色资源
     *
     * @param username
     * @return
     */
    @Override
    public Set<Resource> queryByUserName(String username) {
        username = "admin";
        JSONObject response = orgServiceFeign.queryByUsername(username);
        if (Result.isJsonSuccess(response)) {
            JSONArray data = response.getJSONArray("data");
            return data.stream().map(resource -> JSON.parseObject(JSON.toJSONString(resource), Resource.class)).collect(toSet());
        }
        return new HashSet<>();
    }

    /**
     * 创建RequestMatcher
     *
     * @param pattern
     * @param method
     * @return
     */
    private RequestMatcher requestMatcher(String pattern, String method) {
        return new AntPathRequestMatcher(pattern, method);
    }
}
