package com.runyuanj.authorization.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "demo.security")
@Configuration
@Data
public class SecurityProperties  {
    /**
     * 跳转登录页面(使用了thymeleaf,写在了controler中,不使用的话,可以直接配置为html)
     */
    private String loginPage = "/auth/login.html";

    /**
     * 失败页面(需要带参数,否则无法从session中取到错误信息)
      */
    private String failureUrl = "/auth/login?error=true";

    private String loginProcessingUrl = "/auth/login";

    private String logoutUrl = "/auth/logout";

    /**
     * 默认登录页
     */
    private String loginPageHtml = "signin";

    private String[] matchers = new String[]{"/auth/requrie",
            "/login",
            "/auth/toLogin",
            "/auth/logout",
            "/images/**",
            "/api/**",
            "/auth/token",
            "/logout",
            "/js/**",
            "/iview/**",
            "/css/**"
    };
}