package com.runyuanj.authorization.filter.service;

import org.springframework.security.core.Authentication;

import java.security.Permission;
import java.util.List;

/**
 *
 *
 * @author runyu
 */
public interface ResourcePermissionAuthenticationService {

    /**
     * 获取用户访问的资源对应的权限
     *
     * @param resourcePath
     * @return
     */
    List<Permission> loadResourcePermissions(String resourcePath);

    /**
     * 获取用户拥有的权限
     *
     * @param authentication
     * @return
     */
    List<Permission> loadUserPermissions(Authentication authentication);

    /**
     * 判断是否拥有权限
     * @param resourcePermissions
     * @param userPermissions
     * @return
     */
    boolean hasPermission(List<Permission> resourcePermissions, List<Permission> userPermissions);
}
