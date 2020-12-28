package com.runyuanj.authorization.filter.service.impl;

import com.runyuanj.authorization.filter.service.ResourcePermissionAuthenticationService;
import com.runyuanj.authorization.service.ResourcePermissionService;
import com.runyuanj.core.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 从Redis或Authentication中获取权限信息
 *
 * @author runyu
 */
@Slf4j
@Service
public class ResourcePermissionAuthenticationServiceImpl implements ResourcePermissionAuthenticationService {

    @Autowired
    private ResourcePermissionService resourcePermissionService;

    /**
     * 资源对应的权限需要缓存在本地
     * 获取用户访问的资源信息
     *
     * @param request
     * @return
     */
    @Override
    public ConfigAttribute loadResourcePermissions(HttpServletRequest request) {
        // 获取request请求的资源信息
        return resourcePermissionService.findConfigAttributes(request);
    }

    /**
     * 获取用户拥有的权限
     *
     * @param authentication
     * @return
     */
    @Override
    public Set<Resource> loadUserPermissions(Authentication authentication) {
        // ((JwtAuthenticationToken)authentication).getPrincipal().getUsername()
        UserDetails userDetails = (UserDetails) authentication.getDetails();
        Set<Resource> userResources = findResourcesByUsername(userDetails.getUsername());
        return userResources;
    }

    /**
     * 判断是否拥有权限
     *
     * @param configAttribute
     * @param userResources
     * @return
     */
    @Override
    public boolean hasPermission(ConfigAttribute configAttribute, Set<Resource> userResources) {
        return userResources.stream().anyMatch(resource -> resource.getCode().equals(configAttribute.getAttribute()));
    }

    private Set<com.runyuanj.core.auth.Resource> findResourcesByUsername(String username) {
        // 用户被授予的角色资源
        Set<com.runyuanj.core.auth.Resource> resources = resourcePermissionService.queryByUserName(username);
        log.debug("用户被授予角色的资源数量是:{}, 资源集合信息为:{}", resources.size(), resources);
        return resources;
    }
}
