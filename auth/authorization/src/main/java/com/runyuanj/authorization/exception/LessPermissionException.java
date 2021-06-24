package com.runyuanj.authorization.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 缺少权限
 *
 * @author runyu
 */
public class LessPermissionException extends AuthenticationException {
    /**
     * Constructs an {@code AuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg the detail message
     * @param t   the root cause
     */
    public LessPermissionException(String msg, Throwable t) {
        super(msg, t);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public LessPermissionException(String msg) {
        super(msg);
    }
}
