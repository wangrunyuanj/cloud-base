package com.runyuanj.common.exception;

import com.runyuanj.common.BaseException;
import com.runyuanj.common.ErrorType;

public class ServiceException extends BaseException {

    public ServiceException() {
        super();
    }

    public ServiceException(ErrorType errorType) {
        super(errorType);
    }

    public ServiceException(ErrorType errorType, String message) {
        super(errorType, message);
    }

    public ServiceException(ErrorType errorType, String message, Throwable cause) {
        super(errorType, message, cause);
    }

    //TODO 对业务异常的返回码进行校验，规范到一定范围内
}
