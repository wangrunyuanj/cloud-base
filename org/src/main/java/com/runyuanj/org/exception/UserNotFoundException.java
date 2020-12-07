package com.runyuanj.org.exception;


import com.runyuanj.common.BaseException;

import static com.runyuanj.org.exception.OrgErrorType.USER_NOT_FOUND;

/**
 * @author Administrator
 */
public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }

    public UserNotFoundException(String message) {
        super(USER_NOT_FOUND, message);
    }
}
