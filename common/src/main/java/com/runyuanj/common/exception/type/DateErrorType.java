package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.*;

@Getter
public enum DateErrorType implements ErrorType {
    DATE_FORMAT_ERROR(1001, "日期格式化错误"),
    DATE_SCOPE_ERROR(1002, "时间范围错误");

    private int preCode = DATE_ERROR_CODE_PRE;
    private int subCode;
    /**
     * type code
     */
    private int code;

    /**
     * type message
     */
    private String msg;

    DateErrorType(int subCode, String msg) {
        this.subCode = validate(subCode);
        this.code = preCode * ERROR_CODE_UNIT + subCode;
        this.msg = msg;
    }
}
