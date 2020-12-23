package com.runyuanj.authorization.filter.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * 封装 jwt filter
 *
 * @author runyu
 */
@Slf4j
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String token;
    private UserDetails userDetails;
    private HttpServletRequest request;

    public JwtAuthenticationToken(String token, HttpServletRequest request) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.token = token;
        this.request = request;
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
     * token本身没有权限, 但是在完成权限校验后就能获取到用户的角色信息
     *
     * @return
     */
    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

}
