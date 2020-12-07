package com.runyuanj.org.exception;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

/**
 * @author Administrator
 */

@Getter
public enum OrgErrorType implements ErrorType {

    USER_NOT_FOUND("Org_0100", "用户未找到！"),
    ROLE_NOT_FOUND("Org_200", "角色未找到！");

    /**
     * 错误类型码
     */
    private String code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    OrgErrorType(String code, String mesg) {
        this.code = code;
        this.msg = mesg;
    }
}
