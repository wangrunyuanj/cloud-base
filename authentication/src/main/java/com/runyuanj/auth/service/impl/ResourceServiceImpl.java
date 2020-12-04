package com.runyuanj.auth.service.impl;

import com.runyuanj.auth.service.NewMvcRequestMatcher;
import com.runyuanj.auth.service.ResourceService;
import com.runyuanj.common.response.Result;
import com.runyuanj.core.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.runyuanj.auth.utils.Constants.NONE_URL;

/**
 * 用于从org服务获取资源权限信息
 */
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private HandlerMappingIntrospector mvcHandlerMappingIntrospector;

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
        Result<Set<Resource>> resourceResult = getOrgResources();
        if (resourceResult.isFail()) {
            System.exit(1);
        }
        Set<Resource> resources = resourceResult.getData();
        Map<MvcRequestMatcher, SecurityConfig> temResources = resources.stream().collect(Collectors.toMap(
                resource -> this.newMvcRequestMatcher(resource.getUrl(), resource.getMethod()),
                resource -> new SecurityConfig(resource.getCode())
        ));
        localConfigAttributes.putAll(temResources);
    }

    private Result<Set<Resource>> getOrgResources() {
        return Result.success(new HashSet<Resource>());
    }

    /**
     * 查询method访问对应对应的资源信息
     *
     * @param request
     * @return
     */
    @Override
    public ConfigAttribute findConfigAttributes(HttpServletRequest request) {
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
