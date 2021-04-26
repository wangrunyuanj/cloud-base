package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.DB_ERROR_CODE_PRE;
import static com.runyuanj.common.constant.ErrorCodeConstants.ERROR_CODE_UNIT;

@Getter
public enum DbErrorType implements ErrorType {

    DUPLICATE_PRIMARY_KEY(1001, "唯一键冲突");

    private int preCode = DB_ERROR_CODE_PRE;
    private int subCode;
    private int code;
    private String msg;

    DbErrorType(int subCode, String msg) {
        this.subCode = validate(subCode);
        this.code = this.preCode * ERROR_CODE_UNIT + subCode;
        this.msg = msg;
    }
}