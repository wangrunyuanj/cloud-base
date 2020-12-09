package com.runyuanj.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Administrator
 * @EnableGlobalMethodSecurity 为controller层方法添加权限控制:
 * @PreAuthorize("hasAuthority('course_teachplan_add')")
 */
@Configuration
@EnableWebSecurity
@Order(-1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略哪些资源不用security来管理
        web.ignoring().antMatchers("/login", "/logout", "/jwt");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //直接使用它默认的manager
        AuthenticationManager manager = super.authenticationManagerBean();
        return manager;
    }

    /**
     * 采用bcrypt对密码进行编码,也可以new 一个MD5加密, 看需要
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //配置策略,比如防止csrf攻击,根据需求,不配就使用默认的也行
        http.csrf().disable()
                .httpBasic()
                .and()
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/auth/permission").permitAll()
                .anyRequest().
                authenticated();

    }
}