package com.runyuanj.authorization.filter.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 验证token是否有效
 *
 * @author runyu
 */
public interface TokenAuthenticationService {

    /**
     * 验证token是否有效
     *
     * @param token
     * @return
     */
    UserDetails validate(String token);

}
