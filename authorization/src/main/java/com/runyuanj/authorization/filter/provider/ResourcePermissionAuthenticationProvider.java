package com.runyuanj.authorization.filter.provider;

import com.runyuanj.authorization.entity.MyUser;
import com.runyuanj.authorization.exception.LessAccountException;
import com.runyuanj.authorization.exception.MethodNotAllowedException;
import com.runyuanj.authorization.filter.service.ResourcePermissionAuthenticationService;
import com.runyuanj.authorization.filter.token.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
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

        ConfigAttribute configAttribute = permissionAuthenticationService.loadResourcePermissions(token.getRequest());

        if ("EMPTY".equals(configAttribute.getAttribute())) {
            log.info("未在资源池中找到请求地址. requestPath: {}, method: {}", token.getRequest().getRequestURI(), token.getRequest().getMethod());
            throw new MethodNotAllowedException("未找到指定资源");
        }
        // configAttribute只包含角色id
        // 如果缺少用户信息并且资源允许USER角色访问, 则直接通过
        String roleCodes = configAttribute.getAttribute();
        if (StringUtils.isEmpty(roleCodes)) {
            log.error("请求的资源未被设置权限");
            return null;
        }
        List<String> sourceRoles = Arrays.asList(roleCodes.split(","));
        MyUser myUser = (MyUser) authentication.getDetails();

        if (myUser == null ) {
            if (sourceRoles.contains("USER")) {
                return authentication;
            } else {
                throw new LessAccountException("请先注册登录");
            }
        }

        Set<String> roles = permissionAuthenticationService.loadUserRoles(myUser.getUserId());

        boolean hasPermission = permissionAuthenticationService.hasPermission(sourceRoles, roles);
        if (hasPermission) {
            /**
             * 必须设置, 否则会被最后一个FilterSecurityInterceptor拦截. 拦截点位于AbstractSecurityInterceptor第229行
             */
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
            return authentication;
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
