package com.runyuanj.org.exception;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.ORG_ERROR_CODE_PRE;

/**
 * @author Administrator
 */

@Getter
public enum OrgErrorType implements ErrorType {

    USER_NOT_FOUND(ORG_ERROR_CODE_PRE + 1100, "用户未找到！"),
    ROLE_NOT_FOUND(ORG_ERROR_CODE_PRE + 1200, "角色未找到！");

    /**
     * 错误类型码
     */
    private int code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    OrgErrorType(int code, String mesg) {
        this.code = code;
        this.msg = mesg;
    }
}
