package com.runyuanj.org.exception;

import com.runyuanj.common.ErrorType;
import lombok.Getter;

import static com.runyuanj.common.constant.ErrorCodeConstants.ERROR_CODE_UNIT;
import static com.runyuanj.common.constant.ErrorCodeConstants.ORG_ERROR_CODE_PRE;


/**
 * @author Administrator
 */

@Getter
public enum OrgErrorType implements ErrorType {

    USER_NOT_FOUND(1100, "用户未找到！"),
    ROLE_NOT_FOUND(1200, "角色未找到！");

    private int preCode = ORG_ERROR_CODE_PRE;

    private int subCode;

    private int code;

    private String msg;

    OrgErrorType(int subCode, String msg) {
        this.subCode = validate(subCode);
        this.code = preCode * ERROR_CODE_UNIT + subCode;
        this.msg = msg;
    }
}
