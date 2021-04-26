package com.runyuanj.common;

import java.security.InvalidParameterException;

/**
 * 定义错误码和错误信息
 * 错误码由错误类型 + 子码组成
 * 错误类型见 {@code ErrorCodeConstants}
 */
public interface ErrorType {

    /**
     * @return error type code
     */
    int getCode();

    /**
     * @return error type pre code
     */
    int getPreCode();

    /**
     * @return error type sub code
     */
    int getSubCode();

    /**
     * @return error message
     */
    String getMsg();

    /**
     * 校验
     *
     * @param code
     * @return
     */
    default int validate(int code) {
        if (code < 0 || code > 9999) {
            throw new InvalidParameterException("subCode range: 0 ~ 9999");
        }
        return code;
    }

}
