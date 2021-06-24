package com.runyuanj.authorization.config;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ConfigurationProperties(prefix = "filter")
@PropertySource("classpath:bootstrap-security.yml")
@Configuration
public class SecurityProperties {

    private String loginPage;
    private String failureUrl;
    private String logoutUrl;
    private String indexPage;

    public String[] getMatchers() {
        ArrayList<String> matchers = new ArrayList<>();
        matchers.add(loginPage);
        matchers.add(failureUrl);
        matchers.add(logoutUrl);
        matchers.add(indexPage);
        List<String> collect = matchers.stream().filter(url -> StringUtils.isNotBlank(url)).collect(Collectors.toList());
        return collect.toArray(new String[collect.size()]);
    }

    //    /**
//     * 跳转登录页面(使用了thymeleaf,写在了controler中,不使用的话,可以直接配置为html)
//     */
//    private String loginPage = "/auth/login.html";
//
//    /**
//     * 失败页面(需要带参数,否则无法从session中取到错误信息)
//     */
//    private String failureUrl = "/auth/login?error=true";
//
//    private String loginProcessingUrl = "/auth/login";
//
//    private String logoutUrl = "/auth/logout";
//
//    /**
//     * 默认登录页
//     */
//    private String loginPageHtml = "signin";

//    private String[] matchers = new String[]{
//            "/login",
//            "/auth/toLogin",
//            "/auth/logout",
//            "/images/**",
//            "/api/**",
//            "/logout",
//            "/js/**",
//            "/view/**",
//            "/css/**"
//    };

}