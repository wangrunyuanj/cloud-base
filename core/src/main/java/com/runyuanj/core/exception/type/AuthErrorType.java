package com.runyuanj.core.exception.type;

import com.runyuanj.core.ErrorType;
import lombok.Getter;

@Getter
public enum AuthErrorType implements ErrorType {

    PASSWORD_ERROR("Auth_0001", "密码错误"),
    USER_NOT_EXSITS("Auth_0002", "用户不存在"),
    INVALID_TOKEN("Auth_0003", "无效token");

    AuthErrorType (String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

}
