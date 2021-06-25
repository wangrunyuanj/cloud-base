package com.runyuanj.gateway.service.impl;

import com.runyuanj.auth.api.service.impl.AuthService;
import com.runyuanj.gateway.service.IPermissionService;
import com.runyuanj.gateway.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PermissionService implements IPermissionService {

    @Autowired
    private AuthService authService;

    /**
     * 校验权限, 在本地缓存权限数据.
     * TODO 当用户权限或资源角色权限发生变更时, 需要确保及时清除缓存
     *
     * @param authentication
     * @param url
     * @param method
     * @return
     */
    @Override
    @Cacheable(value = Constants.HAS_PERMISSION_CACHE, key = "#authentication + '_' + #url + '_' + #method")
    public boolean permission(String authentication, String url, String method) {
        return authService.hasPermission(authentication, url, method);
    }
}
