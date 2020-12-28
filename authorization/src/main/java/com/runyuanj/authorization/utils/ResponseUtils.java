package com.runyuanj.authorization.utils;

import com.alibaba.fastjson.JSON;
import com.runyuanj.common.exception.type.AuthErrorType;
import com.runyuanj.common.response.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {

    public static void writeResponseJson(HttpServletResponse response, int httpStatus,  Result result) throws IOException {
        response.setStatus(httpStatus);
        response.setHeader("Content-Type", "application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
