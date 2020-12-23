package com.runyuanj.authorization.controller;

import com.runyuanj.authorization.service.ResourcePermissionService;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限验证
 *
 * @author runyu
 */
@Controller
@Slf4j
public class PermissionController {

    @Autowired
    private ResourcePermissionService resourcePermissionService;

    @PostMapping(value = "/auth/permission")
    // @Cached(expire = 10, localLimit = 20000)
    public Result auth(@RequestParam String url, @RequestParam String method, HttpServletRequest request) {

        resourcePermissionService.auth();
        return Result.success();
    }
}
