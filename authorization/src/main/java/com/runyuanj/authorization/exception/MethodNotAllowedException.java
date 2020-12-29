package com.runyuanj.authorization.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 方法不允许
 *
 * @author runyu
 */
public class MethodNotAllowedException extends AuthenticationException {
    /**
     * Constructs an {@code AuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg the detail message
     * @param t   the root cause
     */
    public MethodNotAllowedException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public MethodNotAllowedException(String msg) {
        super(msg);
    }
}
