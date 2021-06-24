package com.runyuanj.authorization.exception;

import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.response.Result;
import com.runyuanj.core.web.advice.MiddleExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@Order(5)
@ControllerAdvice
public class AuthExceptionHandlerAdvice extends MiddleExceptionHandlerAdvice {

    @ExceptionHandler(value = BadCredentialsException.class)
    public Result badCredentialsException(Exception e) {
        log.error("BadCredentialsException: {}", e.getMessage());
        return Result.fail(AuthErrorType.INVALID_TOKEN);
    }

}