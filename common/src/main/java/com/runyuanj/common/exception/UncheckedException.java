package com.runyuanj.common.exception;

import com.runyuanj.common.BaseException;
import com.runyuanj.common.ErrorType;

/**
 * @author Administrator
 */
public class UncheckedException extends BaseException {

    public UncheckedException() {
        super();
    }

    public UncheckedException(ErrorType errorType) {
        super(errorType);
    }

    public UncheckedException(ErrorType errorType, String message) {
        super(errorType, message);
    }

    public UncheckedException(ErrorType errorType, String message, Throwable cause) {
        super(errorType, message, cause);
    }
}