package com.runyuanj.core.jwt.model;

import lombok.Data;
import java.util.Date;

@Data
public class AuthInfo {

    // (value = "令牌")
    private String token;
    // (value = "令牌类型")
    private String tokenType;
    // (value = "刷新令牌")
    private String refreshToken;
    // (value = "用户名")
    private String name;
    // (value = "账号名")
    private String account;
    // (value = "头像")
    private String avatar;
    // (value = "工作描述")
    private String workDescribe;
    // (value = "用户id")
    private Long userId;
    // (value = "过期时间（秒）")
    private long expire;
    // (value = "到期时间")
    private Date expiration;
}
