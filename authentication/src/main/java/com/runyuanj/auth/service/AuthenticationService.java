package com.runyuanj.auth.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    /**
     * 检查权限
     *
     * @param authRequest
     * @return
     */
    boolean hasPermission(HttpServletRequest authRequest);

}
