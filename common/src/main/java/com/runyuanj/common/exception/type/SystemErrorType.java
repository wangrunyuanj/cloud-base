package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;
import static com.runyuanj.common.constant.ErrorCodeConstants.SYSTEM_ERROR_CODE_PRE;
import static com.runyuanj.common.constant.ErrorCodeConstants.DATE_ERROR_CODE_PRE;

@Getter
public enum SystemErrorType implements ErrorType {

    SYSTEM_ERROR(SYSTEM_ERROR_CODE_PRE + 0000, "系统异常"),
    SYSTEM_BUSY(SYSTEM_ERROR_CODE_PRE + 0001, "系统繁忙,请稍候再试"),
    GATEWAY_CONNECT_TIME_OUT(SYSTEM_ERROR_CODE_PRE + 0002, "网关超时"),
    PARAM_INVALID(SYSTEM_ERROR_CODE_PRE + 0003, "参数异常"),
    SERVICE_RUN_TIMEOUT(SYSTEM_ERROR_CODE_PRE + 0004, "服务运行超时"),

    GATEWAY_NOT_FOUND_SERVICE(SYSTEM_ERROR_CODE_PRE + 0404, "服务未找到"),

    UPLOAD_FILE_SIZE_LIMIT(SYSTEM_ERROR_CODE_PRE + 1010, "上传文件大小超过限制"),
    UPLOAD_FILE_COUNT_LIMIT(SYSTEM_ERROR_CODE_PRE + 1011, "上传文件数量超过限制"),

    GATEWAY_ERROR(SYSTEM_ERROR_CODE_PRE + 7000, "网关异常"),
    FEIGN_ERROR(SYSTEM_ERROR_CODE_PRE + 7001, "服务间异常"),
    SERVICE_CONNECT_TIMEOUT(SYSTEM_ERROR_CODE_PRE + 7002, "服务连接超时"),

    DATE_FORMAT_ERROR(DATE_ERROR_CODE_PRE + 0001, "日期格式化错误"),
    DATE_SCOPE_ERROR(DATE_ERROR_CODE_PRE + 0002, "时间范围错误");

    /**
     * type code
     */
    private int code;

    /**
     * type message
     */
    private String msg;

    SystemErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
