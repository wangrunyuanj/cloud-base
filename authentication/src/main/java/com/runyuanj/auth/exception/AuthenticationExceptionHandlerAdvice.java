package com.runyuanj.auth.exception;

import com.runyuanj.core.web.advice.MiddleExceptionHandlerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthenticationExceptionHandlerAdvice extends MiddleExceptionHandlerAdvice {

}