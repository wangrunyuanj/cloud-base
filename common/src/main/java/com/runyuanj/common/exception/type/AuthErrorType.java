package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.AUTH_ERROR_CODE_PRE;

@Getter
public enum AuthErrorType implements ErrorType {

    AUTHORIZATION_FAILED(AUTH_ERROR_CODE_PRE + 0000, "认证失败"),
    INVALID_REQUEST(AUTH_ERROR_CODE_PRE + 0001, "非法请求"),
    INVALID_CLIENT(AUTH_ERROR_CODE_PRE + 0002, "无效的client_id"),
    INVALID_GRANT(AUTH_ERROR_CODE_PRE + 0003, "无效的授权"),
    INVALID_SCOPE(AUTH_ERROR_CODE_PRE + 0004, "无效scope"),
    EMPTY_TOKEN(AUTH_ERROR_CODE_PRE + 0005, "空token"),
    EXPIRED_TOKEN(AUTH_ERROR_CODE_PRE + 0006, "token已过期"),
    INVALID_TOKEN(AUTH_ERROR_CODE_PRE + 0007, "无效token"),
    INSUFFICIENT_SCOPE(AUTH_ERROR_CODE_PRE + 0010, "权限不足"),
    REDIRECT_URI_MISMATCH(AUTH_ERROR_CODE_PRE + 0020, "redirect url不匹配"),
    ACCESS_DENIED(AUTH_ERROR_CODE_PRE + 0030, "拒绝访问"),
    METHOD_NOT_ALLOWED(AUTH_ERROR_CODE_PRE + 0040, "不支持该方法"),
    SERVER_ERROR(AUTH_ERROR_CODE_PRE + 0050, "权限服务错误"),
    UNAUTHORIZED_CLIENT(AUTH_ERROR_CODE_PRE + 0060, "未授权客户端"),
    UNAUTHORIZED(AUTH_ERROR_CODE_PRE + 0061, "未授权"),
    UNSUPPORTED_RESPONSE_TYPE(AUTH_ERROR_CODE_PRE + 0070, " 支持的响应类型"),
    UNSUPPORTED_GRANT_TYPE(AUTH_ERROR_CODE_PRE + 0071, "不支持的授权类型"),

    USERNAME_PASSWORD_AUTH_FAILED(AUTH_ERROR_CODE_PRE + 0100, "用户名或密码错误"),
    PASSWORD_ERROR(AUTH_ERROR_CODE_PRE + 0101, "密码错误"),
    USER_NOT_EXIST(AUTH_ERROR_CODE_PRE + 0102, "用户不存在");

    /**
     * 错误类型码
     */
    private int code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    AuthErrorType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
