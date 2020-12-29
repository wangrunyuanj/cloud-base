package com.runyuanj.authorization.service;

import org.springframework.security.core.Authentication;

public interface RefreshTokenService {

    /**
     * 更新token
     *
     * @param authentication JwtAuthenticationToken
     * @return
     */
    String refreshToken(Authentication authentication);

    /**
     * 判断是否需要刷新token
     *
     * @param authentication JwtAuthenticationToken
     * @return
     */
    boolean shouldRefresh(Authentication authentication);
}
