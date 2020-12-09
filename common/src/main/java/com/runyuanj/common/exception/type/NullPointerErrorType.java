package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

/**
 * @author Administrator
 */
@Getter
public enum NullPointerErrorType implements ErrorType {

    NULL_POINTER("Null_0000", "空指针异常");

    NullPointerErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;
}
