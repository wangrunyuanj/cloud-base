package com.runyuanj.core.exception.type;

import com.runyuanj.core.ErrorType;
import lombok.Getter;

@Getter
public enum DbErrorType implements ErrorType {

    DUPLICATE_PRIMARY_KEY("Db_0001", "唯一键冲突");

    DbErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;
}