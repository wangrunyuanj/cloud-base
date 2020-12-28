package com.runyuanj.authorization.handler;

import com.runyuanj.authorization.utils.ResponseUtils;
import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.response.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonLogiuFailureHandler implements AuthenticationFailureHandler {
    /**
     * Called when an authentication attempt fails.
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseUtils.writeResponseJson(response, 401, Result.fail(AuthErrorType.USERNAME_PASSWORD_AUTH_FAILED));
    }
}
