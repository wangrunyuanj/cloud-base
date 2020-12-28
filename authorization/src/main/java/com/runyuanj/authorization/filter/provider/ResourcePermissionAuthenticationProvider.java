package com.runyuanj.authorization.filter.provider;

import com.runyuanj.authorization.filter.service.ResourcePermissionAuthenticationService;
import com.runyuanj.authorization.filter.token.JwtAuthenticationToken;
import com.runyuanj.core.auth.Resource;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Permission;
import java.util.List;
import java.util.Set;

import static com.runyuanj.authorization.utils.Constants.NONE_URL;
import static com.runyuanj.common.exception.type.AuthErrorType.EXPIRED_TOKEN;
import static com.runyuanj.common.exception.type.AuthErrorType.INVALID_TOKEN;

/**
 *
 *
 * @author runyu
 */
@Slf4j
public class ResourcePermissionAuthenticationProvider implements AuthenticationProvider {

    private ResourcePermissionAuthenticationService permissionAuthenticationService;

    public ResourcePermissionAuthenticationProvider(ResourcePermissionAuthenticationService permissionAuthenticationService) {
        this.permissionAuthenticationService = permissionAuthenticationService;
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
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        try {
            ConfigAttribute configAttribute = permissionAuthenticationService.loadResourcePermissions(token.getRequest());

            if (NONE_URL.equals(configAttribute.getAttribute())) {
                log.info("url未在资源池中找到. requestPath: {}, method: {}", token.getRequest().getPathInfo(), token.getRequest().getMethod());
                return null;
            }

            Set<Resource> userPermissions = permissionAuthenticationService.loadUserPermissions(token);

            boolean hasPermission = permissionAuthenticationService.hasPermission(configAttribute, userPermissions);
            if (hasPermission) {
                return authentication;
            }
        } catch (Exception e) {
            log.error("token校验异常, 请联系管理员", e);
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
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication)) ||
                (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
