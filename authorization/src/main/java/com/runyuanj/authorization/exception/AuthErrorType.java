package com.runyuanj.authorization.exception;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

@Getter
public enum AuthErrorType implements ErrorType {

    INVALID_REQUEST("Auth-0001", "无效请求"),
    INVALID_CLIENT("Auth-0002", "无效client_id"),
    INVALID_GRANT("Auth-0003", "无效授权"),
    INVALID_SCOPE("Auth-0004", "无效scope"),
    INVALID_TOKEN("Auth-0005", "无效token"),
    INSUFFICIENT_SCOPE("Auth-0010", "授权不足"),
    REDIRECT_URI_MISMATCH("Auth-0020", "redirect url不匹配"),
    ACCESS_DENIED("Auth-0030", "拒绝访问"),
    METHOD_NOT_ALLOWED("Auth-0040", "不支持该方法"),
    SERVER_ERROR("Auth-0050", "权限服务错误"),
    UNAUTHORIZED_CLIENT("Auth-0060", "未授权客户端"),
    UNAUTHORIZED("Auth-0061", "未授权"),
    UNSUPPORTED_RESPONSE_TYPE("Auth-0070", " 支持的响应类型"),
    UNSUPPORTED_GRANT_TYPE("040071", "不支持的授权类型"),

    AUTH_FAILED("Auth-0001", "用户名或密码错误");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    AuthErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
