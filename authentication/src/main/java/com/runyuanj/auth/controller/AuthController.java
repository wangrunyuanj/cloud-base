package com.runyuanj.auth.controller;

import com.alicp.jetcache.anno.Cached;
import com.runyuanj.auth.model.HttpServletRequestAuthWrapper;
import com.runyuanj.auth.service.AuthenticationService;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
public class AuthController {

    @Resource
    private AuthenticationService authenticationService;

    @GetMapping("/check")
    public Result checkHealth() {
        return Result.success();
    }


    @PostMapping(value = "/auth/permission")
    @Cached(expire = 10, localLimit = 20000)
    public Result auth(@RequestParam String url, @RequestParam String method, HttpServletRequest request) {

        boolean result = authenticationService.hasPermission(new HttpServletRequestAuthWrapper(request, url, method));
        return Result.success(result);
    }

}
