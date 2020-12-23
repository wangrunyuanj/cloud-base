package com.runyuanj.authorization.filter.service.impl;

import com.runyuanj.authorization.filter.service.ResourcePermissionAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Permission;
import java.util.Collections;
import java.util.List;

/**
 * 从Redis或Authentication中获取权限信息
 * @author runyu
 */
@Slf4j
@Service
public class ResourcePermissionAuthenticationServiceImpl implements ResourcePermissionAuthenticationService {

    /**
     * 资源对应的权限需要从本地缓存一部分
     * 获取用户访问的资源对应的权限
     *
     * @param resourcePath
     * @return
     */
    @Override
    public List<Permission> loadResourcePermissions(String resourcePath) {

        return Collections.emptyList();
    }

    /**
     * 获取用户拥有的权限
     *
     * @param authentication
     * @return
     */
    @Override
    public List<Permission> loadUserPermissions(Authentication authentication) {

        return Collections.emptyList();
    }

    /**
     * 判断是否拥有权限
     *
     * @param resourcePermissions
     * @param userPermissions
     * @return
     */
    @Override
    public boolean hasPermission(List<Permission> resourcePermissions, List<Permission> userPermissions) {
        log.info("resource has permission");
        return true;
    }
}
