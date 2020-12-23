package com.runyuanj.cloud.cache.exception;

import com.runyuanj.common.ErrorType;
import lombok.AllArgsConstructor;
import static com.runyuanj.common.constant.ErrorCodeConstants.CACHE_ERROR_CODE_PRE;

@AllArgsConstructor
public enum CacheErrorType implements ErrorType {

    CLASS_NOT_FOUND(CACHE_ERROR_CODE_PRE + 0002, "未找到指定类"),
    CACHE_NOT_FOUND(CACHE_ERROR_CODE_PRE + 0001, "缓存未命中");

    private int code;

    private String msg;


    /**
     * @return type code
     */
    @Override
    public int getCode() {
        return this.code;
    }

    /**
     * @return type message
     */
    @Override
    public String getMsg() {
        return this.msg;
    }
}
