package com.runyuanj.core;

import lombok.Getter;

import static com.runyuanj.core.exception.type.SystemErrorType.SYSTEM_ERROR;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Getter
public class BaseException extends RuntimeException {

    /**
     * 异常对应的错误类型
     */
    private final ErrorType errorType;

    private final String message;

    /**
     * 默认是系统异常
     */
    public BaseException() {
        this.errorType = SYSTEM_ERROR;
        this.message = SYSTEM_ERROR.getMsg();
    }

    public BaseException(ErrorType errorType) {
        this.errorType = errorType;
        this.message = errorType.getMsg();
    }

    public BaseException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
        if (isNotBlank(message)) {
            this.message = message;
        } else {
            this.message = errorType.getMsg();
        }
    }

    public BaseException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
        if (isNotBlank(message)) {
            this.message = message;
        } else {
            this.message = errorType.getMsg();
        }
    }
}
