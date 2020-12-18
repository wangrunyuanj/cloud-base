package com.runyuanj.authorization.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 生成用户token
 * @author Administrator
 */
public interface JwtTokenService {

    String getUserJwt(UserDetails userDetails);
}
