package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.CACHE_ERROR_CODE_PRE;
import static com.runyuanj.common.constant.ErrorCodeConstants.NULL_POINTER_ERROR_CODE_PRE;

/**
 * @author Administrator
 */
@Getter
public enum NullPointerErrorType implements ErrorType {

    NULL_POINTER(1000, "null pointer exception");

    private int preCode = NULL_POINTER_ERROR_CODE_PRE;
    private int subCode;
    private int code;
    private String msg;

    NullPointerErrorType(int subCode, String msg) {
        this.subCode = validate(subCode);
        this.code = preCode + subCode;
        this.msg = msg;
    }
}
