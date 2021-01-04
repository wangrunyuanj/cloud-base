package com.runyuanj.authorization.filter.provider;

import com.runyuanj.authorization.filter.token.JwtAuthorization;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author runyu
 */
public class LoginAuthenticationProvider implements AuthenticationProvider {


    private UserDetailsService userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthorization.class.isAssignableFrom(authentication));
    }

    public void setUserDetailService(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;

    }
}
