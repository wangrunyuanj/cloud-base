package com.runyuanj.auth.server.service;

import com.runyuanj.core.auth.Resource;
import org.springframework.security.access.ConfigAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface IResourceService {

    /**
     * 获取此url, method对应的权限资源信息
     *
     * @param authRequest
     * @return
     */
    ConfigAttribute findConfigAttributesByUrl(HttpServletRequest authRequest);

    /**
     * 根据用户名查询资源权限信息
     *
     * @param username
     * @return
     */
    Set<Resource> queryByUsername(String username);
}
