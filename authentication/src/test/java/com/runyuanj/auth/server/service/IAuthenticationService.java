package com.runyuanj.auth.server.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验权限
 */
public interface IAuthenticationService {

    /**
     * 校验权限
     *
     * @param authRequest
     * @return
     */
    boolean auth(HttpServletRequest authRequest);

}
