package com.runyuanj.authorization.handler;

import com.runyuanj.authorization.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功后处理方法
 *
 * @author Administrator
 */
@Slf4j
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenService jwtTokenService;

    public SecurityAuthenticationSuccessHandler(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("authentication success! {}", request.getPathInfo());
        // 如果是外部服务, 没有请求头, 则为其加上Authorization
        // String token = jwtTokenService.getUserJwt((UserDetails) authentication.getPrincipal());
        // response.setHeader("Authorization", token);
    }
}
