package com.runyuanj.cloud.cache.exception;

import com.runyuanj.common.BaseException;
import com.runyuanj.common.ErrorType;

/**
 * 缓存中的异常
 *
 * @author Administrator
 */
public class CacheException extends BaseException {

    public CacheException() {
        super();
    }

    public CacheException(ErrorType errorType) {
        super(errorType);
    }

    public CacheException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
