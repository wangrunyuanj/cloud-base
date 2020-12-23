package com.runyuanj.authorization.filter.manager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * 判断用户是否具有访问该资源的权限
 *
 * @author runyu
 */
public class ResourcePermissionAuthenticationManager  implements AuthenticationManager {

    private List<AuthenticationProvider> providers;

    public ResourcePermissionAuthenticationManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationException exception;
        Authentication result = null;

        try {
            for (AuthenticationProvider provider : providers) {
                boolean supports = provider.supports(authentication.getClass());
                if (!supports) {
                    continue;
                }
                result = provider.authenticate(authentication);
                if (result != null) {
                    break;
                }
            }
        } catch (AuthenticationException e) {
            exception = e;
        }
        return result;
    }
}
