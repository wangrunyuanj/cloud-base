package com.runyuanj.authorization.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author runyu
 */
@Slf4j
public class SimpleAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * Called when an authentication attempt fails.
     *
     * @param request  the request during which the authentication attempt occurred.
     * @param response the response.
     * @param e        the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.info("authentication failed, {}", request.getPathInfo(), e);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
