package com.runyuanj.authorization.filter.token;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 封装 jwt filter
 *
 * @author runyu
 */
@Slf4j
public class JwtAuthorization extends AbstractAuthenticationToken implements AuthenticationTrustResolver {

    private boolean anonymous;

    private boolean rememberMe;

    private String token;
    private UserDetails userDetails;
    private HttpServletRequest request;

    public JwtAuthorization(String token, HttpServletRequest request, UserDetails userDetails,
                            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.request = request;
        this.userDetails = userDetails;
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

    @Override
    public boolean isAnonymous(Authentication authentication) {
        return this.anonymous;
    }

    @Override
    public boolean isRememberMe(Authentication authentication) {
        return this.rememberMe;
    }
}
