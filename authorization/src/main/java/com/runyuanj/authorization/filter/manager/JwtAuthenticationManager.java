package com.runyuanj.authorization.filter.manager;

import com.runyuanj.authorization.filter.provider.JwtAuthenticationProvider;
import com.runyuanj.authorization.filter.provider.LoginAuthenticationProvider;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author runyu
 *
 * 提供验证
 */
public class JwtAuthenticationManager extends ProviderManager {

    public JwtAuthenticationManager(List<AuthenticationProvider> providers) {
        super(providers);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 需要做额外的异常处理, 处理DisabledException, LockedException, BadCredentialsException等异常
        // 不需要在校验失败时返回错误. 而是继续进行下一步资源权限校验
        AuthenticationException exception;
        Authentication result = null;

        try {
            for (AuthenticationProvider provider : getProviders()) {
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
