package com.runyuanj.cloud.cache.exception;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.CACHE_ERROR_CODE_PRE;
import static com.runyuanj.common.constant.ErrorCodeConstants.ERROR_CODE_UNIT;

@Getter
public enum CacheErrorType implements ErrorType {

    CLASS_NOT_FOUND(1002, "未找到指定类"),
    CACHE_NOT_FOUND(1001, "缓存未命中");

    CacheErrorType(int subCode, String msg) {
        this.subCode = validate(subCode);
        this.code = preCode * ERROR_CODE_UNIT + subCode;
        this.msg = msg;
    }

    private int preCode = CACHE_ERROR_CODE_PRE;
    private int subCode;
    private int code;
    private String msg;
}
