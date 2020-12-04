package com.runyuanj.auth.service;

import com.runyuanj.common.response.Result;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

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
