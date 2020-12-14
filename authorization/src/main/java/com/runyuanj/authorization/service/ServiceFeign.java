package com.runyuanj.authorization.service;

import com.alibaba.fastjson.JSONObject;
import com.runyuanj.authorization.config.DefaultFeignConfiguration;
import com.runyuanj.authorization.entity.Role;
import com.runyuanj.authorization.entity.User;
import com.runyuanj.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(value = "org", configuration = DefaultFeignConfiguration.class, fallback = ServiceFeign.ServiceFeignFallback.class)
public interface ServiceFeign {

    @GetMapping(value = "/user")
    Result<User> getUserByUniqueId(@RequestParam("uniqueId") String uniqueId);

    @GetMapping(value = "/role/user/{userId}")
    Result<Set<Role>> queryRolesByUserId(@PathVariable("userId") String userId);


    class ServiceFeignFallback {
        Result<User> getUserByUniqueId(String uniqueId) {
            return Result.fail();
        }

        Result<Set<Role>> queryRolesByUserId(String userId) {
            return Result.fail();
        }
    }

}
