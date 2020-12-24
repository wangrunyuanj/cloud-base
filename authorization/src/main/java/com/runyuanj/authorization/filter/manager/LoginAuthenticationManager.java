package com.runyuanj.authorization.filter.manager;

import com.runyuanj.authorization.filter.provider.LoginAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理登录
 * 可以使用默认的manager, 在多个providers中选择需要的那个.
 *
 * @author runyu
 */
public class LoginAuthenticationManager implements AuthenticationManager {

    private List<AuthenticationProvider> providers;

    private AuthenticationProvider authenticationProvider = new LoginAuthenticationProvider();

    public LoginAuthenticationManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
        if (this.providers == null || this.providers.isEmpty()) {
            this.providers = new ArrayList<>();
            this.providers.add(authenticationProvider);
        }
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
