package com.runyuanj.core.exception.type;

import com.runyuanj.core.exception.type.ErrorType;
import lombok.Getter;

@Getter
public enum SystemErrorType implements ErrorType {

    SYSTEM_ERROR("-1", "系统异常"),
    SYSTEM_BUSY("Sys_0001", "系统繁忙,请稍候再试"),

    GATEWAY_CONNECT_TIME_OUT("Sys_0002", "网关超时"),
    GATEWAY_NOT_FOUND_SERVICE("Sys_0404", "服务未找到"),
    GATEWAY_ERROR("Sys_0500", "网关异常"),

    PARAM_INVALID("Sys_0003", "参数异常"),
    SERVICE_RUN_TIMEOUT("Sys_0004", "服务运行超时"),
    UPLOAD_FILE_COUNT_LIMIT("Sys_0013", "上传文件数量超过限制"),
    UPLOAD_FILE_SIZE_LIMIT("Sys_0014", "上传文件大小超过限制");


    /**
     * type code
     */
    private String code;

    /**
     * type message
     */
    private String msg;

    SystemErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
