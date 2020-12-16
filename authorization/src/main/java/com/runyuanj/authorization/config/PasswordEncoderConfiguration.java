package com.runyuanj.authorization.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

import static org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion.$2B;

@Configuration
public class PasswordEncoderConfiguration {

    /**
     * 采用bcrypt对密码进行编码,也可以new一个MD5加密, 看需要
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder($2B, 4, new SecureRandom());
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder($2B, 4, new SecureRandom());
        String encode = passwordEncoder.encode("12345678");
        System.out.println(encode);
    }

}
