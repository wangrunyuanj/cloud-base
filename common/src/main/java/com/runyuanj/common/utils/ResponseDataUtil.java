package com.runyuanj.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.runyuanj.common.exception.ResponseException;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;

import static com.runyuanj.common.exception.type.ResponseErrorType.*;

/**
 * @author: runyu
 * @date: 2019/12/5 9:23
 */
@Slf4j
public class ResponseDataUtil {

    public static <T> T parseObjectByType(Result response, Class<T> clazz) {
        try {
            Object data = handleResponse(response);
            if (data != null) {
                return JSONObject.parseObject(JSONObject.toJSONString(data), clazz);
            }
        } catch (Exception e) {
            log.error("QueryContentInfoServiceImpl::parseResponse Error: {}", e.getMessage());
        }
        return null;
    }

    public static JSONObject parseObjectResponse(Result response) {
        try {
            Object data = handleResponse(response);
            if (data != null) {
                return JSONObject.parseObject(JSONObject.toJSONString(data));
            }
        } catch (Exception e) {
            log.error("QueryContentInfoServiceImpl::parseResponse Error: {}", e.getMessage());
        }
        return null;
    }

    public static JSONArray parseArrayResponse(Result response) {
        try {
            Object data = handleResponse(response);
            if (data != null) {
                return JSONObject.parseArray(JSONObject.toJSONString(data));
            }
        } catch (Exception e) {
            log.error("QueryContentInfoServiceImpl::parseResponse Error: {}", e.getMessage());
        }
        return null;
    }

    public static JSONObject parseObjectFromArray(Result response) {
        JSONArray jsonArray = parseArrayResponse(response);
        if (jsonArray != null && !jsonArray.isEmpty()) {
            return (JSONObject) jsonArray.get(0);
        }
        return null;
    }

    public static Object handleResponse(Result response) throws ResponseException {
        if (response == null) {
            throw new ResponseException(NON_RESPONSE);
        }
        if (response.getCode() == null) {
            throw new ResponseException(ERROR_FORMAT);
        }
        if (response.isFail()) {
            throw new ResponseException(TARGET_SERVICE_ERROR, "Service Error!"
                    + " Error Code: " + response.getCode()
                    + " Msg: " + response.getMessage()
                    + " Data: " + JSON.toJSONString(response.getData()));
        }
        if (response.getData() == null) {
            throw new ResponseException(EMPTY_DATA);
        }
        return response.getData();
    }
}
