package com.runyuanj.authorization.utils;

import com.alibaba.fastjson.JSON;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class AuthResponseUtils {

    public static void writeResponseJson(HttpServletResponse response, int httpStatus, Result result) throws IOException {
        response.setStatus(httpStatus);
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        try {
            writer.write(JSON.toJSONString(result));
            writer.flush();
        } catch (Exception e) {
            if (writer != null) {
                try {
                    writer.close();
                } catch (Exception e1) {
                    log.error("close output stream error", e1);
                }
            }
        }
    }
}
