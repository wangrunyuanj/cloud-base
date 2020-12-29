package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;
import static com.runyuanj.common.constant.ErrorCodeConstants.NULL_POINTER_ERROR_CODE_PRE;

/**
 * @author Administrator
 */
@Getter
public enum NullPointerErrorType implements ErrorType {

    NULL_POINTER(NULL_POINTER_ERROR_CODE_PRE + 1000, "空指针异常");

    NullPointerErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;
}
