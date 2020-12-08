package com.runyuanj.common.exception;

import com.runyuanj.common.BaseException;
import com.runyuanj.common.ErrorType;

/**
 * @author Administrator
 */
public class ResponseException extends BaseException {

    public ResponseException() {
        super();
    }

    public ResponseException(ErrorType errorType) {
        super(errorType);
    }

    public ResponseException(ErrorType errorType, String message) {
        super(errorType, message);
    }

    public ResponseException(ErrorType errorType, String message, Throwable cause) {
        super(errorType, message, cause);
    }

}
