package com.runyuanj.authorization.handler;

import com.runyuanj.authorization.exception.LessAccountException;
import com.runyuanj.authorization.exception.LessPermissionException;
import com.runyuanj.authorization.exception.MethodNotAllowedException;
import com.runyuanj.authorization.utils.ResponseUtils;
import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.exception.type.SystemErrorType;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.info("authentication failed, uri: {}, method: {}, message: {}", request.getRequestURI(), request.getMethod(), e.getMessage());
        Result result;
        if (e instanceof MethodNotAllowedException) {
            result = Result.fail(AuthErrorType.METHOD_NOT_ALLOWED);
        } else if (e instanceof LessAccountException) {
            result = Result.fail(AuthErrorType.LESS_ACCOUNT);
        } else if (e instanceof LessPermissionException) {
            result = Result.fail(AuthErrorType.LESS_PERMISSION);
        } else if (e instanceof AuthenticationServiceException) {
            result = Result.fail(AuthErrorType.SERVER_ERROR);
        } else if (e instanceof DisabledException) {
            result = Result.fail(AuthErrorType.ACCOUNT_DISABLED);
        } else if (e instanceof AuthenticationException) {
            result = Result.fail(AuthErrorType.AUTHORIZATION_FAILED);
        } else {
            log.error("exception type: {}", e.getClass().getName());
            result = Result.fail(SystemErrorType.SYSTEM_ERROR);
        }
        ResponseUtils.writeResponseJson(response, 401, result);
    }
}
