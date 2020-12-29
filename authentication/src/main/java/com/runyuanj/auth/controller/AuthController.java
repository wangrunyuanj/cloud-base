package com.runyuanj.auth.controller;

import com.runyuanj.auth.model.HttpServletRequestAuthWrapper;
import com.runyuanj.auth.service.AuthenticationService;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class AuthController {

    @Resource
    private AuthenticationService authenticationService;

    @GetMapping("/check")
    public Result checkHealth() {
        return Result.success();
    }

    @PostMapping(value = "/auth/permission")
    // @Cached(expire = 10, localLimit = 20000)
    public Result auth(@RequestParam String url, @RequestParam String method, HttpServletRequest request) {

        boolean result = authenticationService.hasPermission(new HttpServletRequestAuthWrapper(request, url, method));
        return Result.success(result);
    }

}
