package com.runyuanj.authorization.filter.provider;

import com.runyuanj.authorization.filter.service.TokenAuthenticationService;
import com.runyuanj.authorization.filter.token.JwtAuthorization;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import static com.runyuanj.common.exception.type.AuthErrorType.EXPIRED_TOKEN;
import static com.runyuanj.common.exception.type.AuthErrorType.INVALID_TOKEN;

/**
 * 从redis中取token, 如果存在, 则验证通过. 不存在则验证失败
 * 参考DaoAuthenticationProvider等
 *
 * @author runyu
 */
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private TokenAuthenticationService tokenAuthenticationService;

    public JwtAuthenticationProvider(TokenAuthenticationService tokenAuthenticationService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the JwtAuthorization 包含了token
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 此处不适合抛出 DisabledException, LockedException, BadCredentialsException异常
        Authentication result = null;
        String token = (String) authentication.getCredentials();
        try {
            UserDetails userDetails = tokenAuthenticationService.validate(token);
            // 封装到JwtAuthorization.
            ((JwtAuthorization) authentication).setDetails(userDetails);
            result = authentication;
            // 不应设置isAuthenticated = true.
            return result;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e1) {
            log.info("JwtAuthenticationProvider authenticate failed. code: {}, message: {}, exception: {}",
                    INVALID_TOKEN.getCode(), INVALID_TOKEN.getMsg(), e1.getClass().getName());
        } catch (ExpiredJwtException e2) {
            log.info("JwtAuthenticationProvider authenticate failed. code: {}, message: {}, exception: {}",
                    EXPIRED_TOKEN.getCode(), EXPIRED_TOKEN.getMsg(), e2.getClass().getName());
            result = authentication;
        } catch (Exception e) {
            log.error("JwtAuthenticationProvider token校验异常, 请联系管理员", e);
        }
        return result;
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthorization.class.isAssignableFrom(authentication));
    }
}
