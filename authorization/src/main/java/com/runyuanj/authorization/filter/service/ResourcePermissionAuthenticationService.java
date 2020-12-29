package com.runyuanj.authorization.filter.service;

import com.runyuanj.core.auth.Resource;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.security.Permission;
import java.util.List;
import java.util.Set;

/**
 *
 *
 * @author runyu
 */
public interface ResourcePermissionAuthenticationService {

    /**
     * 获取用户访问的资源对应的权限
     *
     * @param request
     * @return
     */
    ConfigAttribute loadResourcePermissions(HttpServletRequest request);

    /**
     * 获取用户拥有的权限
     *
     * @param authentication
     * @return
     */
    Set<Resource> loadUserPermissions(Authentication authentication);

    /**
     * 判断是否拥有权限
     * 缺少权限则抛出异常
     *
     * @param resourceRoles
     * @param userRoles
     * @return
     */
    boolean hasPermission(List<String> resourceRoles, Set<String> userRoles);

    /**
     * 根据用户Id查询角色Id
     *
     * @param userId
     * @return
     */
    Set<String> loadUserRoles(String userId);
}
