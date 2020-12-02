package com.runyuanj.auth.server.provider;

import com.runyuanj.core.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * TODO
 * 默认从本地缓存和集中缓存查询资源信息. 当缓存不存在时, 从org服务查询资源信息
 */
@FeignClient(name = "org", fallback = ResourceProviderFallback.class)
public interface ResourceProvider {

    @GetMapping(value = "/resource/all")
    Result resources();

    @GetMapping(value = "/resource/user/{username}")
    Result resources(@PathVariable("username") String username);
}
