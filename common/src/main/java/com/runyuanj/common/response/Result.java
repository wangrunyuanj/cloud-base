package com.runyuanj.common.response;

import com.alibaba.fastjson.JSONObject;
import com.runyuanj.common.BaseException;
import com.runyuanj.common.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

import static com.runyuanj.common.exception.type.SystemErrorType.SYSTEM_ERROR;

@Data
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "0";
    public static final String SUCCESS_MSG = "success";

    private String code;

    private String message;

    private Instant time;

    private T data;

    private Result(String code) {
        this.code = code;
        this.time = ZonedDateTime.now().toInstant();
    }

    private Result(String code, String message) {
        this(code);
        this.message = message;
    }

    public Result(String code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public Result(ErrorType errorType) {
        this(errorType.getCode(), errorType.getMsg());
    }

    public Result(ErrorType errorType, T data) {
        this(errorType);
        this.data = data;
    }

    public Result(ErrorType errorType, String message) {
        this(errorType.getCode(), message);
    }

    public Result(ErrorType errorType, String message, T data) {
        this(errorType, message);
        this.data = data;
    }

    /**
     * success
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        return new Result(SUCCESS_CODE, null, data);
    }

    /**
     * fail
     *
     * @return
     */
    public static Result fail() {
        return fail(SYSTEM_ERROR, SYSTEM_ERROR.getMsg(), null);
    }

    public static Result fail(ErrorType errorType) {
        return fail(errorType, errorType.getMsg(), null);
    }

    public static Result fail(ErrorType errorType, Object data) {
        return fail(errorType, errorType.getMsg(), data);
    }

    public static Result fail(ErrorType errorType, String message, Object data) {
        return new Result(errorType, message, data);
    }

    /**
     * fail
     *
     * @param baseException
     * @return
     */
    private static Result fail(BaseException baseException) {
        return fail(baseException.getErrorType(), baseException.getMessage(), null);
    }

    public static Result fail(BaseException baseException, Object data) {
        return fail(baseException.getErrorType(), baseException.getMessage(), data);
    }

    public static Result fail(BaseException baseException, String message, Object data) {
        return new Result(baseException.getErrorType(), message, data);
    }

    public static boolean isJsonSuccess(JSONObject responseEntity) {
        if (responseEntity != null && SUCCESS_CODE.equals(responseEntity.getString("code"))) {
            return true;
        }
        return false;
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
