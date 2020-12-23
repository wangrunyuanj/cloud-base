package com.runyuanj.authorization.filter.manager;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

/**
 * @author runyu
 *
 * 提供验证
 */
public class JwtAuthenticationManager implements AuthenticationManager {

    private List<AuthenticationProvider> providers;

    public JwtAuthenticationManager(List<AuthenticationProvider> providers) {
        this.providers = providers;
    }

    /**
     * Attempts to authenticate the passed {@link Authentication} object, returning a
     * fully populated <code>Authentication</code> object (including granted authorities)
     * if successful.
     * <p>
     * An <code>AuthenticationManager</code> must honour the following contract concerning
     * exceptions:
     * <ul>
     * <li>A {@link DisabledException} must be thrown if an account is disabled and the
     * <code>AuthenticationManager</code> can test for this state.</li>
     * <li>A {@link LockedException} must be thrown if an account is locked and the
     * <code>AuthenticationManager</code> can test for account locking.</li>
     * <li>A {@link BadCredentialsException} must be thrown if incorrect credentials are
     * presented. Whilst the above exceptions are optional, an
     * <code>AuthenticationManager</code> must <B>always</B> test credentials.</li>
     * </ul>
     * Exceptions should be tested for and if applicable thrown in the order expressed
     * above (i.e. if an account is disabled or locked, the authentication request is
     * immediately rejected and the credentials testing process is not performed). This
     * prevents credentials being tested against disabled or locked accounts.
     *
     * @param authentication the authentication request object
     * @return a fully authenticated object including credentials
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 需要做额外的异常处理, 处理DisabledException, LockedException, BadCredentialsException等异常
        // 不需要在校验失败时返回错误. 而是继续进行下一步资源权限校验
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
