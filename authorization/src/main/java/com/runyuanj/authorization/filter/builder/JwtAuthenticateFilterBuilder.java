package com.runyuanj.authorization.filter.builder;

import com.runyuanj.authorization.filter.JwtAuthenticationFilter;
import com.runyuanj.authorization.filter.manager.JwtAuthenticationManager;
import com.runyuanj.authorization.filter.provider.JwtAuthenticationProvider;
import com.runyuanj.authorization.filter.service.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author runyu
 */
public class JwtAuthenticateFilterBuilder {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private RequestMatcher requestMatcher;
    private List<AuthenticationProvider> authenticationProviders = new ArrayList<>();

    public JwtAuthenticateFilterBuilder() {
    }

    public JwtAuthenticationFilter build() {
        JwtAuthenticationManager jwtAuthenticationManager = new JwtAuthenticationManager(authenticationProviders);
        return new JwtAuthenticationFilter(jwtAuthenticationManager, requestMatcher);
    }

    public JwtAuthenticateFilterBuilder authenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }

    public JwtAuthenticateFilterBuilder authenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        return this;
    }

    public JwtAuthenticateFilterBuilder requestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
        return this;
    }

    public JwtAuthenticateFilterBuilder authenticationProvider(TokenAuthenticationService tokenAuthenticationService) {
        AuthenticationProvider provider = new JwtAuthenticationProvider(tokenAuthenticationService);
        this.authenticationProviders.add(provider);
        return this;
    }

}
