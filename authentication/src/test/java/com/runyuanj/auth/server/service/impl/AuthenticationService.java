package com.runyuanj.auth.server.service.impl;

import com.runyuanj.auth.server.service.IAuthenticationService;
import com.runyuanj.auth.server.service.IResourceService;
import com.runyuanj.core.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Service
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    /**
     * 未在资源库中的URL默认标识
     */
    public static final String NONEXISTENT_URL = "NONEXISTENT_URL";


    @Autowired
    private IResourceService resourceService;

    /**
     * 校验权限
     *
     * @param authRequest
     * @return
     */
    @Override
    public boolean auth(HttpServletRequest authRequest) {
        String method = authRequest.getMethod();
        String url = authRequest.getServletPath();
        log.debug("正在访问的url是:{}，method:{}", authRequest.getServletPath(), authRequest.getMethod());

        //获取用户认证信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //获取此url，method访问对应的权限资源信息
        ConfigAttribute urlConfigAttribute = resourceService.findConfigAttributesByUrl(authRequest);
        if (NONEXISTENT_URL.equals(urlConfigAttribute.getAttribute())) {
            log.debug("url未在资源池中找到，拒绝访问");
        }

        //获取此访问用户所有角色拥有的权限资源
        Set<Resource> userResources = findResourcesByUsername(authentication.getName());

        return false;
    }


    /**
     * 根据用户所被授予的角色，查询到用户所拥有的资源
     *
     * @param username
     * @return
     */
    private Set<Resource> findResourcesByUsername(String username) {
        //用户被授予的角色资源
        Set<Resource> resources = resourceService.queryByUsername(username);
        if (log.isDebugEnabled()) {
            log.debug("用户被授予角色的资源数量是:{}, 资源集合信息为:{}", resources.size(), resources);
        }
        return resources;
    }
}
