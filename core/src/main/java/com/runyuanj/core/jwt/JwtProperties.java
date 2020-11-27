package com.runyuanj.core.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.runyuanj.core.jwt.JwtProperties.PREFIX;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class JwtProperties {
    public static final String PREFIX = "authentication";

    /**
     * 过期时间 2h
     */
    private Long expire = 7200L;
    /**
     * 刷新token的过期时间 8h
     */
    private Long refreshExpire = 28800L;

}