package com.runyuanj.common.constant;

public class ErrorCodeConstants {

    public static final int ERROR_CODE_UNIT = 10000;

    public static final int SYSTEM_ERROR_CODE_PRE = 100 * ERROR_CODE_UNIT;
    public static final int DB_ERROR_CODE_PRE = 110 * ERROR_CODE_UNIT;
    public static final int CACHE_ERROR_CODE_PRE = 120 * ERROR_CODE_UNIT;
    public static final int DATE_ERROR_CODE_PRE = 130 * ERROR_CODE_UNIT;
    public static final int RESPONSE_ERROR_CODE_PRE = 199 * ERROR_CODE_UNIT;
    public static final int NULL_POINTER_ERROR_CODE_PRE = 190 * ERROR_CODE_UNIT;
    public static final int AUTH_ERROR_CODE_PRE = 401 * ERROR_CODE_UNIT;
    public static final int ORG_ERROR_CODE_PRE = 810 * ERROR_CODE_UNIT;
}
