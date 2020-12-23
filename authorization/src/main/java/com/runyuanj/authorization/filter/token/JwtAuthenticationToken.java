package com.runyuanj.authorization.filter.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 封装 jwt filter
 *
 * @author runyu
 */
@Slf4j
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private UserDetails userDetails;

    public JwtAuthenticationToken(String token) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
        this.userDetails = null;
    }

    public JwtAuthenticationToken(String token, UserDetails userDetails) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.userDetails = userDetails;
        this.token = token;
    }

    /**
     * token凭证
     *
     * @return
     */
    @Override
    public Object getCredentials() {
        return token;
    }

    /**
     * 用户基础信息与权限
     *
     * @return
     */
    @Override
    public Object getPrincipal() {
        return userDetails;
    }
}