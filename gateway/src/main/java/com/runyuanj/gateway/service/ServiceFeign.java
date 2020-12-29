package com.runyuanj.gateway.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "authentication", fallback = ServiceFeign.ServiceFeignFallback.class)
public interface ServiceFeign {

    @PostMapping("/auth/permission")
    JSONObject hasPermission(@RequestHeader(HttpHeaders.AUTHORIZATION) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);

    class ServiceFeignFallback {

        JSONObject queryAll() {
            return new JSONObject();
        }
    }

}
