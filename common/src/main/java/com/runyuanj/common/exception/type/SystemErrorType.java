package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.*;

@Getter
public enum SystemErrorType implements ErrorType {

    SYSTEM_ERROR(1000, "系统异常"),
    SYSTEM_BUSY(1001, "系统繁忙,请稍候再试"),
    GATEWAY_CONNECT_TIME_OUT(1002, "网关超时"),
    PARAM_INVALID(1003, "参数异常"),
    SERVICE_RUN_TIMEOUT(1004, "服务运行超时"),

    GATEWAY_NOT_FOUND_SERVICE(0404, "服务未找到"),

    UPLOAD_FILE_SIZE_LIMIT(1010, "上传文件大小超过限制"),
    UPLOAD_FILE_COUNT_LIMIT(1011, "上传文件数量超过限制"),

    GATEWAY_ERROR(7000, "网关异常"),
    FEIGN_ERROR(7001, "服务间异常"),
    SERVICE_CONNECT_TIMEOUT(7002, "服务连接超时");

    private int preCode = SYSTEM_ERROR_CODE_PRE;
    private int subCode;
    /**
     * type code
     */
    private int code;

    /**
     * type message
     */
    private String msg;

    SystemErrorType(int subCode, String msg) {
        this.subCode = validate(subCode);
        this.code = preCode * ERROR_CODE_UNIT + subCode;
        this.msg = msg;
    }
}
