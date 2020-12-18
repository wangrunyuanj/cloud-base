package com.runyuanj.common;

/**
 * 不同的错误类型有不同的 value * 1000 作为错误编码前缀
 */
public interface ErrorName {

    String getName();

    int value();
}
