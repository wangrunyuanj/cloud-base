package com.runyuanj.authorization.service.impl;

import com.runyuanj.authorization.filter.token.JwtTokenComponent;
import com.runyuanj.authorization.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * 刷新token, 生成新的token, 更新redis中的token
 *
 * @author runyu
 */
@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private JwtTokenComponent jwtTokenComponent;

    /**
     * 更新token
     *
     * @param authentication JwtAuthenticationToken
     * @return
     */
    @Override
    public String refreshToken(Authentication authentication) {
        return "";
    }

    /**
     * 判断是否需要刷新token
     *
     * @param authentication JwtAuthenticationToken
     * @return
     */
    @Override
    public boolean shouldRefresh(Authentication authentication) {
        return false;
    }
}
