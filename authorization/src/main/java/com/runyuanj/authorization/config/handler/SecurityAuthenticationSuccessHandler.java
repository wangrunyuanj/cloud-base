package com.runyuanj.authorization.config.handler;

import com.runyuanj.authorization.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenService jwtTokenService;

    public SecurityAuthenticationSuccessHandler(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("authentication success! {}", request.getPathInfo());
        String token = jwtTokenService.getUserJwt((UserDetails) authentication.getPrincipal());
        response.setHeader("Authorization", token);
    }
}
