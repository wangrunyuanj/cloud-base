package com.runyuanj.authorization.filter.provider;

import com.runyuanj.authorization.exception.AuthErrorType;
import com.runyuanj.authorization.filter.token.JwtAuthenticationToken;
import com.runyuanj.authorization.filter.service.TokenAuthenticationService;
import com.runyuanj.common.response.Result;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

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
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication auth;
        Result result;
        try {
            String authResult = tokenAuthenticationService.validate((String) authentication.getCredentials());

            // 封装到AuthenticationToken
            return new JwtAuthenticationToken(authResult);
        } catch (
                ExpiredJwtException e1) {
            result = Result.fail(AuthErrorType.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            result = Result.fail(AuthErrorType.INVALID_TOKEN);
        }

        return null;
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
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
