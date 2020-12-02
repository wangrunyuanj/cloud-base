package com.runyuanj.cloud.cache.exception;

import com.runyuanj.common.ErrorType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CacheErrorType implements ErrorType {

    CLASS_NOT_FOUND("Cache_0002", "未找到指定类"),
    CACHE_NOT_FOUND("Cache_0001", "缓存未找到");

    private String code;

    private String msg;


    /**
     * @return type code
     */
    @Override
    public String getCode() {
        return null;
    }

    /**
     * @return type message
     */
    @Override
    public String getMsg() {
        return null;
    }
}
