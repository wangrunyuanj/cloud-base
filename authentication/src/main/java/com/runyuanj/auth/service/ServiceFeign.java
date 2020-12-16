package com.runyuanj.auth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.runyuanj.common.response.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Administrator
 */
@FeignClient(value = "org", fallback = ServiceFeign.ServiceFeignFallback.class)
public interface ServiceFeign {

    @GetMapping("/resource/all")
    JSONObject queryAll();

    @GetMapping("/resource/user/{username}")
    JSONObject queryByUsername(@PathVariable("username") String username);

    /**
     * 请求用户信息
     *
     * @param uniqueId
     * @return JSONObject -> Result<User>
     */
    @GetMapping(value = "/user")
    JSONObject getUserByUniqueId(@RequestParam("uniqueId") String uniqueId);

    /**
     * 获取用户角色信息
     *
     * @param userId
     * @return JSONObject -> Result<Set<Role>>
     */
    @GetMapping(value = "/role/user/{userId}")
    JSONObject queryRolesByUserId(@PathVariable("userId") String userId);


    class ServiceFeignFallback {
        JSONObject queryAll() {
            return JSONObject.parseObject(JSON.toJSONString(Result.fail()));
        }

        JSONObject queryByUsername(String username) {
            return JSONObject.parseObject(JSON.toJSONString(Result.fail()));
        }

        JSONObject getUserByUniqueId(String uniqueId) {
            return JSONObject.parseObject(JSON.toJSONString(Result.fail()));
        }

        JSONObject queryRolesByUserId(String userId) {
            return JSONObject.parseObject(JSON.toJSONString(Result.fail()));
        }
    }

}
