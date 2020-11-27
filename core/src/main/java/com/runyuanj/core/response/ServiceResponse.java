package com.runyuanj.core.response;

import com.runyuanj.core.BaseException;
import com.runyuanj.core.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ServiceResponse<T> {

    public static final String SUCCESS_CODE = "0";
    public static final String SUCCESS_MSG = "success";

    private String code;

    private String message;

    private Instant time;

    private T data;

    private ServiceResponse(String code) {
        this.code = code;
        this.time = ZonedDateTime.now().toInstant();
    }

    private ServiceResponse(String code, String message) {
        this(code);
        this.message = message;
    }

    private ServiceResponse(String code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public ServiceResponse(ErrorType errorType) {
        this(errorType.getCode(),errorType.getMsg());
    }

    public ServiceResponse(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    public ServiceResponse(ErrorType errorType, String message) {
        this(errorType.getCode(), message);
    }

    public ServiceResponse(ErrorType errorType, String message, T data) {
        this(errorType, message);
        this.data = data;
    }

    public static ServiceResponse success() {
        return new ServiceResponse(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public static ServiceResponse success(Object data) {
        return new ServiceResponse(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static ServiceResponse fail(ErrorType errorType, String message, Object data) {
        return new ServiceResponse(errorType, message, data);
    }

    public static ServiceResponse fail(ErrorType errorType, String message) {
        return new ServiceResponse(errorType, message, null);
    }

    public static ServiceResponse fail(ErrorType errorType) {
        return new ServiceResponse(errorType);
    }

    public static ServiceResponse fail(ErrorType errorType, Object data) {
        return new ServiceResponse(errorType, data);
    }

    public static ServiceResponse fail(BaseException baseException, String message, Object data) {
        return new ServiceResponse(baseException.getErrorType(), message, data);
    }

    public static ServiceResponse fail(BaseException baseException, String message) {
        return new ServiceResponse(baseException.getErrorType(), message, null);
    }

    private static ServiceResponse fail(BaseException baseException) {
        return new ServiceResponse(baseException.getErrorType());
    }

    public static ServiceResponse fail(BaseException baseException, Object data) {
        return new ServiceResponse(baseException.getErrorType(), data);
    }

    /**
     * 成功code = 0
     *
     * @return true/false
     */
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.code);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    public boolean isFail() {
        return !isSuccess();
    }
}
