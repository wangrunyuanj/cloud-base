package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;
import static com.runyuanj.common.constant.ErrorCodeConstants.DB_ERROR_CODE_PRE;

@Getter
public enum DbErrorType implements ErrorType {

    DUPLICATE_PRIMARY_KEY(DB_ERROR_CODE_PRE + 0001, "唯一键冲突");

    DbErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;
}