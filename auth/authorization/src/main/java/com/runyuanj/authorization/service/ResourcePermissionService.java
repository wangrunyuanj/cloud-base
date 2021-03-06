package com.runyuanj.authorization.service;

import com.runyuanj.authorization.entity.Resource;
import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Set;

public interface ResourcePermissionService {

    void auth();

    /**
     * 动态新增更新权限
     *
     * @param resource
     */
    void saveResource(Resource resource);

    /**
     * 动态删除权限
     *
     * @param resource
     */
    void removeResource(Resource resource);

    /**
     * 加载权限资源数据
     */
    void loadResource();

    /**
     * 获取所有ConfigAttribute
     *
     * @return
     */
    Collection<ConfigAttribute> getConfigAttributes();

    /**
     * 查询method访问对应对应的资源信息
     *
     * @param request
     * @return
     */
    ConfigAttribute findConfigAttributes(HttpServletRequest request);

    /**
     * 查询用户被授予的角色资源
     *
     * @param username
     * @return
     */
    Set<Resource> queryByUserName(String username);

    /**
     * 根据用户Id查询角色Id
     *
     * @param userId
     * @return
     */
    Set<String> queryRolesByUserId(String userId);
}
