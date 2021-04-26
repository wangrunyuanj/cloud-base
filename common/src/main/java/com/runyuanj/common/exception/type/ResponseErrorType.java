package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.ERROR_CODE_UNIT;
import static com.runyuanj.common.constant.ErrorCodeConstants.RESPONSE_ERROR_CODE_PRE;

/**
 * @author Administrator
 */
@Getter
public enum ResponseErrorType implements ErrorType {

    NON_RESPONSE(1000, "无返回值"),
    EMPTY_DATA(1001, "返回值data为空"),
    TARGET_SERVICE_ERROR(1002, "服务内部错误"),
    ERROR_FORMAT(1003, "返回值格式错误");

    private int preCode = RESPONSE_ERROR_CODE_PRE;
    private int subCode;
    private int code;
    private String msg;

    ResponseErrorType(int subCode, String msg) {
        this.subCode = validate(subCode);
        this.code = preCode * ERROR_CODE_UNIT + subCode;
        this.msg = msg;
    }
}