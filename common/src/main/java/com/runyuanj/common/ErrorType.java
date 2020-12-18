package com.runyuanj.common;

/**
 * TODO
 * 添加一个
 * 添加n * 1000的常量, 定义不同类型的errorType, getCode()参数不同
 */
public interface ErrorType {


    // String name();

    /**
     * @return type code
     */
    String getCode();

    /**
     * @return type message
     */
    String getMsg();

}
