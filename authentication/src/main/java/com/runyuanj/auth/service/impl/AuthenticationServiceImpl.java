package com.runyuanj.auth.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.runyuanj.auth.service.AuthenticationService;
import com.runyuanj.auth.service.ResourceService;
import com.runyuanj.core.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

import static com.runyuanj.auth.utils.Constants.NONE_URL;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 对获取到的用户信息和资源权限信息进行验证
 */
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private ResourceService resourceService;

    /**
     * 检查权限
     *
     * @param authRequest
     * @return
     */
    @Override
    @Cached(name = "auth_check::", key = "#authentication+#method+#url",
            cacheType = CacheType.LOCAL, expire = 10, timeUnit = SECONDS, localLimit = 20000)
    public boolean hasPermission(HttpServletRequest authRequest) {
        // 获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取此url, method对应的资源信息
        ConfigAttribute urlConfigAttribute = resourceService.findConfigAttributes(authRequest);

        if (NONE_URL.equals(urlConfigAttribute.getAttribute())) {
            log.debug("url未在资源池中找到，拒绝访问");
        }

        //获取此访问用户所有角色拥有的权限资源
        Set<Resource> userResources = findResourcesByUsername(authentication.getName());
        //用户拥有权限资源 与 url要求的资源进行对比

        return isMatch(urlConfigAttribute, userResources);
    }

    private boolean isMatch(ConfigAttribute configAttribute, Set<com.runyuanj.core.auth.Resource> userResources) {
        return userResources.stream().anyMatch(resource -> resource.getCode().equals(configAttribute.getAttribute()));
    }

    private Set<com.runyuanj.core.auth.Resource> findResourcesByUsername(String username) {
        // 用户被授予的角色资源
        Set<com.runyuanj.core.auth.Resource> resources = resourceService.queryByUserName(username);
        log.debug("用户被授予角色的资源数量是:{}, 资源集合信息为:{}", resources.size(), resources);
        return resources;
    }
}
