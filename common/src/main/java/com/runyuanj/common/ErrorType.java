package com.runyuanj.common;

import java.security.InvalidParameterException;

/**
 * TODO
 * 添加一个
 * 添加n * 1000的常量, 定义不同类型的errorType, getCode()参数不同
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
