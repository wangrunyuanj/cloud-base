package com.runyuanj.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.runyuanj.auth.config.DefaultFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "org", configuration = DefaultFeignConfiguration.class, fallback = ServiceFeign.ServiceFeignFallback.class)
public interface ServiceFeign {

    @GetMapping("/resource/all")
    JSONObject queryAll();

    @GetMapping("/resource/user/{username}")
    JSONObject queryByUsername(@PathVariable("username") String username);

    class ServiceFeignFallback {

        JSONObject queryAll() {
            return new JSONObject();
        }
    }

}
