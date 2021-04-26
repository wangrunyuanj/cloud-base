package com.runyuanj.common.exception.type;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.AUTH_ERROR_CODE_PRE;
import static com.runyuanj.common.constant.ErrorCodeConstants.ERROR_CODE_UNIT;

@Getter
public enum AuthErrorType implements ErrorType {

    AUTHORIZATION_FAILED(1000, "认证失败"),
    INVALID_REQUEST(1001, "非法请求"),
    INVALID_CLIENT(1002, "无效的client_id"),
    INVALID_GRANT(1003, "无效的授权"),
    INVALID_SCOPE(1004, "无效scope"),
    EMPTY_TOKEN(1005, "空token"),
    EXPIRED_TOKEN(1006, "token已过期"),
    INVALID_TOKEN(1007, "无效token"),
    INSUFFICIENT_SCOPE(1010, "权限不足"),
    REDIRECT_URI_MISMATCH(1020, "redirect url不匹配"),
    ACCESS_DENIED(1030, "拒绝访问"),
    METHOD_NOT_ALLOWED(1040, "不支持该方法"),
    SERVER_ERROR(1050, "权限服务错误"),
    UNAUTHORIZED_CLIENT(1060, "未授权客户端"),
    UNAUTHORIZED(1061, "未授权"),
    UNSUPPORTED_RESPONSE_TYPE(1070, " 支持的响应类型"),
    UNSUPPORTED_GRANT_TYPE(1071, "不支持的授权类型"),
    ACCOUNT_DISABLED(1080, "账号异常"),
    LESS_PERMISSION(1090, "权限不足"),
    LESS_ACCOUNT(1091, "登录后访问"),

    USERNAME_PASSWORD_AUTH_FAILED(1100, "用户名或密码错误"),
    PASSWORD_ERROR(1101, "密码错误"),
    USER_NOT_EXIST(1102, "用户不存在");

    private int preCode = AUTH_ERROR_CODE_PRE;

    private int subCode;

    /**
     * 错误类型码
     */
    private int code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    AuthErrorType(int subCode, String msg) {
        this.subCode = validate(subCode);
        this.code = preCode * ERROR_CODE_UNIT  + subCode;
        this.msg = msg;
    }

}
