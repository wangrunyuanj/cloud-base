package com.runyuanj.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.runyuanj.auth.config.DefaultFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "org", configuration = DefaultFeignConfiguration.class,fallbackFactory = DefaultServiceFallbackFactory.class)
public interface ServiceFeign {

    @GetMapping("/resource/all")
    JSONObject queryAll();

}
