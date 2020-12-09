package com.runyuanj.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.runyuanj.auth.service.NewMvcRequestMatcher;
import com.runyuanj.auth.service.ResourceService;
import com.runyuanj.auth.service.ServiceFeign;
import com.runyuanj.common.response.Result;
import com.runyuanj.common.utils.ResponseDataUtil;
import com.runyuanj.core.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.runyuanj.auth.utils.Constants.NONE_URL;
import static java.util.stream.Collectors.toSet;

/**
 * 用于从org服务获取资源权限信息
 */
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private HandlerMappingIntrospector mvcHandlerMappingIntrospector;
    @Autowired
    private ServiceFeign serviceFeign;



    private static final String ORG_RESOURCE_PATH = "http://org/resource/all";

    /**
     * 系统中所有权限集合
     */
    private static final Map<RequestMatcher, ConfigAttribute> localConfigAttributes = new HashMap<>();

    /**
     * 动态新增更新权限
     *
     * @param resource
     */
    @Override
    public void saveResource(Resource resource) {
        localConfigAttributes.put(
                this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()),
                new SecurityConfig(resource.getCode()));
    }

    /**
     * 动态删除权限
     *
     * @param resource
     */
    @Override
    public void removeResource(Resource resource) {
        localConfigAttributes.remove(this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()));
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
        Map<MvcRequestMatcher, SecurityConfig> temResources = resources.stream().collect(Collectors.toMap(
                resource -> this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()),
                resource -> new SecurityConfig(resource.getCode())
        ));
        localConfigAttributes.putAll(temResources);
    }

    private Set<Resource> getOrgResources() {
        try {
            JSONObject responseEntity = serviceFeign.queryAll();
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
        localConfigAttributes.keySet().stream().filter(requestMatcher -> requestMatcher.matches(request))
                .map(requestMatcher -> localConfigAttributes.get(requestMatcher))
                .peek(urlConfig -> log.debug("url在资源池中配置：{}", urlConfig.getAttribute()))
                .findFirst()
                .orElse(new SecurityConfig(NONE_URL));
        return null;
    }

    /**
     * 向org服务发送请求, 查询用户被授予的角色资源
     *
     * @param username
     * @return
     */
    @Override
    public Set<Resource> queryByUserName(String username) {

        return null;
    }

    /**
     * 创建RequestMatcher
     *
     * @param url
     * @param method
     * @return
     */
    private MvcRequestMatcher newMvcRequestMatcher(String url, String method) {
        return new NewMvcRequestMatcher(mvcHandlerMappingIntrospector, url, method);
    }
}
