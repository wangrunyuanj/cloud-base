package com.runyuanj.authorization.handler;

import com.runyuanj.core.token.JwtTokenComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功后处理方法
 *
 * @author Administrator
 */
@Slf4j
@Service
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenComponent tokenComponent;

    public SecurityAuthenticationSuccessHandler(JwtTokenComponent tokenComponent) {
        this.tokenComponent = tokenComponent;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("authentication success! {}", request.getPathInfo());
        String token = tokenComponent.generalToken((UserDetails) authentication.getPrincipal());
        // response.setHeader("Authorization", token);
    }
}
