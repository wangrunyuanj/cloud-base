package com.runyuanj.authorization.handler.login;

import com.runyuanj.authorization.utils.ResponseUtils;
import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SimpleLoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("authentication failed, {}", request.getPathInfo());
        log.info("SimpleLoginAuthenticationFailureHandler authException name: {}", e.getClass().getName());
        ResponseUtils.writeResponseJson(response, 401, Result.fail(AuthErrorType.USERNAME_PASSWORD_AUTH_FAILED));
    }
}
