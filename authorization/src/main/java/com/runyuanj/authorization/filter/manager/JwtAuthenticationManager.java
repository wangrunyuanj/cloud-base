package com.runyuanj.authorization.filter.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * @author runyu
 * <p>
 * 提供验证
 */
@Slf4j
public class JwtAuthenticationManager extends ProviderManager {

    public JwtAuthenticationManager(List<AuthenticationProvider> providers) {
        super(providers);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 需要做额外的异常处理, 处理DisabledException, LockedException, BadCredentialsException等异常
        // 不需要在校验失败时返回错误. 而是继续进行下一步资源权限校验
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
            log.info("JwtAuthenticationManager.authenticate cause {}, message: {}", e.getClass().getName(), e.getMessage());
            throw e;
        }
        return result;
    }

}
