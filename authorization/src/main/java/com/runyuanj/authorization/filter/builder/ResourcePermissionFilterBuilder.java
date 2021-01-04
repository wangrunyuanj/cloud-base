package com.runyuanj.authorization.filter.builder;

import com.runyuanj.authorization.filter.manager.JwtAuthenticationManager;
import com.runyuanj.authorization.filter.provider.ResourcePermissionAuthenticationProvider;
import com.runyuanj.authorization.filter.service.ResourcePermissionAuthenticationService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

public class ResourcePermissionFilterBuilder {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private RequestMatcher requestMatcher;
    private List<AuthenticationProvider> authenticationProviders = new ArrayList<>();

    public ResourcePermissionFilterBuilder() {
    }

    public ResourcePermissionFilterBuilder authenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        return this;
    }

    public ResourcePermissionFilterBuilder authenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        return this;
    }

    public ResourcePermissionFilterBuilder requestMatcher(RequestMatcher requestMatcher) {
        this.requestMatcher = requestMatcher;
        return this;
    }

    public ResourcePermissionFilterBuilder authenticationProvider(ResourcePermissionAuthenticationService resourcePermissionAuthenticationService) {
        AuthenticationProvider provider = new ResourcePermissionAuthenticationProvider(resourcePermissionAuthenticationService);
        this.authenticationProviders.add(provider);
        return this;
    }

}