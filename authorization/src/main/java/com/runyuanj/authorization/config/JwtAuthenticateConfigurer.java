package com.runyuanj.authorization.config;

import com.runyuanj.authorization.filter.JwtAuthenticationFilter;
import com.runyuanj.authorization.filter.manager.JwtAuthenticationManager;
import com.runyuanj.authorization.filter.provider.JwtAuthenticationProvider;
import com.runyuanj.authorization.filter.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author runyu
 */
@Configuration
@ComponentScan(basePackages = { "com.runyuanj.authorization.**"})
public class JwtAuthenticateConfigurer {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private RequestMatcher requestMatcher;
    private List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    public JwtAuthenticateConfigurer() {
    }

    public JwtAuthenticationFilter toJwtAuthenticationFilter() {
        AuthenticationManager authenticationManager = new JwtAuthenticationManager(authenticationProviders);
        return new JwtAuthenticationFilter(authenticationManager, requestMatcher);
    }

    public JwtAuthenticateConfigurer authenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }

    public JwtAuthenticateConfigurer authenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        return this;
    }

    public JwtAuthenticateConfigurer requestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
        return this;
    }

    public JwtAuthenticateConfigurer authenticationProvider(TokenAuthenticationService tokenAuthenticationService) {
        AuthenticationProvider provider = new JwtAuthenticationProvider(tokenAuthenticationService);
        this.authenticationProviders.add(provider);
        return this;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return this.authenticationProvider(tokenAuthenticationService)
                .requestMatcher(new RequestHeaderRequestMatcher("Authorization"))
                .toJwtAuthenticationFilter();
    }
}
