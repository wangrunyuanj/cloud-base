package com.runyuanj.authorization.config;

import com.runyuanj.authorization.filter.JwtAuthenticationFilter;
import com.runyuanj.authorization.filter.ResourcePermissionFilter;
import com.runyuanj.authorization.filter.manager.JwtAuthenticationManager;
import com.runyuanj.authorization.filter.provider.ResourcePermissionAuthenticationProvider;
import com.runyuanj.authorization.filter.service.ResourcePermissionAuthenticationService;
import com.runyuanj.authorization.handler.EmptyAuthenticationSuccessHandler;
import com.runyuanj.authorization.handler.SimpleAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ResourcePermissionConfigurer {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private RequestMatcher requestMatcher;
    private List<AuthenticationProvider> authenticationProviders = new ArrayList<>();
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ResourcePermissionAuthenticationService resourcePermissionAuthenticationService;

    public ResourcePermissionConfigurer() {
    }

    public ResourcePermissionFilter toResourcePermissionFilter() {
        AuthenticationManager authenticationManager = new JwtAuthenticationManager(authenticationProviders);
        return new ResourcePermissionFilter(authenticationManager, requestMatcher, authenticationSuccessHandler, authenticationFailureHandler);
    }

    public ResourcePermissionConfigurer authenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }

    public ResourcePermissionConfigurer authenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        return this;
    }

    public ResourcePermissionConfigurer requestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
        return this;
    }

    public ResourcePermissionConfigurer authenticationProvider(ResourcePermissionAuthenticationService resourcePermissionAuthenticationService) {
        AuthenticationProvider provider = new ResourcePermissionAuthenticationProvider(resourcePermissionAuthenticationService);
        this.authenticationProviders.add(provider);
        return this;
    }

    @Bean
    public ResourcePermissionFilter resourcePermissionFilter() {
        return this.authenticationProvider(resourcePermissionAuthenticationService)
                .requestMatcher(new RequestHeaderRequestMatcher("Authorization"))
                .authenticationSuccessHandler(new EmptyAuthenticationSuccessHandler())
                .authenticationFailureHandler(new SimpleAuthenticationFailureHandler())
                .toResourcePermissionFilter();
    }
}