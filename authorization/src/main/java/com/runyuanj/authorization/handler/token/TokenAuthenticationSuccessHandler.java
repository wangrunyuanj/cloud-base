package com.runyuanj.authorization.handler.token;

import com.runyuanj.authorization.service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后处理方法
 * 判断是否需要刷新token
 *
 * @author Administrator
 */
@Slf4j
@Component
public class TokenAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("authentication success! {}", request.getPathInfo());
        // 判断token是否需要刷新
        boolean shouldRefresh = refreshTokenService.shouldRefresh(authentication);
        if (shouldRefresh) {
            String newToken = refreshTokenService.refreshToken(authentication);
            response.setHeader("Authentication", newToken);
        }
    }

}
