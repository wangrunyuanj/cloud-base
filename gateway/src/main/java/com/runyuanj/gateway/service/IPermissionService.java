package com.runyuanj.gateway.service;

/**
 * 权限校验
 *
 * @author Administrator
 */
public interface IPermissionService {

    /**
     * 校验权限
     *
     * @param authentication
     * @param url
     * @param method
     * @return
     */
    boolean permission(String authentication, String url, String method);


    /**
     * 是否忽略该url
     *
     * @param url
     * @return
     */
    boolean ignoreAuthentication(String url);
}
