package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;
import static com.runyuanj.common.constant.ErrorCodeConstants.RESPONSE_ERROR_CODE_PRE;

/**
 * @author Administrator
 */
@Getter
public enum ResponseErrorType implements ErrorType {

    NON_RESPONSE(RESPONSE_ERROR_CODE_PRE + 0000, "无返回值"),
    EMPTY_DATA(RESPONSE_ERROR_CODE_PRE + 0001, "返回值data为空"),
    TARGET_SERVICE_ERROR(RESPONSE_ERROR_CODE_PRE + 0002, "服务内部错误"),
    ERROR_FORMAT(RESPONSE_ERROR_CODE_PRE + 0003, "返回值格式错误");

    ResponseErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;
}