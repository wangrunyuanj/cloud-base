package com.runyuanj.auth.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Constants {

    public static final String NONE_URL = "none_url";

    /**
     * 测试密码加密
     * @param args
     */
    public static void main(String[] args) {
        // springsecurity 注册加密方法
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("1");
        System.out.println(encode);
        //$2a$10$H2HTe3SVdKMk8ewC3gRKouva7U6DAQspHqyhcdg805JGHAApV1Wci
        //$2a$10$Iz4Y52GmirUf5SRW6jTIA.0cgaS0mKTYZVN2cFFeK8DXk9YHVhJDW

        // springsecurity 登录加密方法
        BCrypt bCrypt = new BCrypt();
        String hashpw = BCrypt.hashpw("1", "$2a$10$Iz4Y52GmirUf5SRW6jTIA.0cgaS0mKTYZVN2cFFeK8DXk9YHVhJDW");
        System.out.println(hashpw);
    }
}
