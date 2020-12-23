package com.runyuanj.authorization.filter.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * 封装 jwt filter
 *
 * @author runyu
 */
@Slf4j
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;

    public JwtAuthenticationToken(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    /**
     * 用户信息
     *
     * @return
     */
    @Override
    public Object getPrincipal() {
        return token;
    }
}
