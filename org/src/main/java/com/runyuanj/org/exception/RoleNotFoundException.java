package com.runyuanj.org.exception;

import com.runyuanj.common.BaseException;

import static com.runyuanj.org.exception.OrgErrorType.ROLE_NOT_FOUND;

/**
 * @author Administrator
 */
public class RoleNotFoundException extends BaseException {

    public RoleNotFoundException() {
        super(ROLE_NOT_FOUND);
    }

    public RoleNotFoundException(String message) {
        super(ROLE_NOT_FOUND, message);
    }
}
