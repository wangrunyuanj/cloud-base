package com.runyuanj.gateway.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.runyuanj.auth.client.service.IAuthService;
import com.runyuanj.gateway.service.IPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

@Service
@Slf4j
public class PermissionService implements IPermissionService {

    @Autowired
    private IAuthService authService;

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
    @Cached(name = "gateway_auth::", key = "#authentication+#method+#url",
        cacheType = CacheType.LOCAL, expire = 10, timeUnit = SECONDS, localLimit = 20000)
    public boolean permission(String authentication, String url, String method) {
        return authService.hasPermission(authentication, url, method);
    }
}
