package com.runyuanj.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class ConfigController {

    @Value("spring.datasource.driver-class-name")
    private boolean driverClassName;

    @GetMapping("/get")
    public boolean get() {
        return driverClassName;
    }
}
