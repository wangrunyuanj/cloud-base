package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

/**
 * @author Administrator
 */
@Getter
public enum ResponseErrorType implements ErrorType {

    NON_RESPONSE("Resp_0000", "无返回值"),
    EMPTY_DATA("Resp_0001", "返回值data为空"),
    TARGET_SERVICE_ERROR("Resp_0002", "服务内部错误"),
    ERROR_FORMAT("Resp_0003", "返回值格式错误");

    ResponseErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;
}