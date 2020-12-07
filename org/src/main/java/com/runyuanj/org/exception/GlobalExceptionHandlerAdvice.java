package com.runyuanj.org.exception;

import com.runyuanj.common.response.Result;
import com.runyuanj.core.web.advice.ExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerAdvice extends ExceptionHandlerAdvice {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public Result userNotFound(UserNotFoundException e) {
        log.error(e.getMessage());
        return Result.fail(e.getErrorType());
    }

    @ExceptionHandler(value = {RoleNotFoundException.class})
    public Result roleNotFound(RoleNotFoundException e) {
        log.error(e.getMessage());
        return Result.fail(e.getErrorType());
    }
}