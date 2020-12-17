package com.runyuanj.authorization.controller;

import com.runyuanj.common.response.Result;
import com.runyuanj.core.context.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@RestController
@Slf4j
public class LoginController {

    @GetMapping("/hello")
    public Result hello() {
        UserDetails userDetails = UserContextHolder.userDetails();
        log.info("userDetails: {}", userDetails);
        return Result.success("hello! authentication = " + SecurityContextHolder.getContext().getAuthentication());
    }

    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        log.info("login: username: {} , password: {}", username, password);
        return Result.success();
    }

    @GetMapping("/user")
    @ResponseBody
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/error")
    @ResponseBody
    public String error(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }

}
