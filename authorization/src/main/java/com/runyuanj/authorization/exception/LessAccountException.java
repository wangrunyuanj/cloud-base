package com.runyuanj.authorization.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 需要登录
 *
 * @author runyu
 */
public class LessAccountException extends AuthenticationException {

    /**
     * Constructs an {@code AuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg the detail message
     * @param t   the root cause
     */
    public LessAccountException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public LessAccountException(String msg) {
        super(msg);
    }
}
