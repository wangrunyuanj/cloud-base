package com.runyuanj.authorization.access;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Collections;

public class RoleBasedVoter implements AccessDecisionVoter<Object> {

    private static final String ANONYMOUS = "USER";

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return SecurityConfig.class.isAssignableFrom(attribute.getClass());
    }

    /**
     * 必须全部完成遍历. 只要有一个不通过, 就抛出异常.
     *
     * @param authentication 用户验证, 包括用户拥有的角色列表
     * @param object         FilterInvocation的子类. 属性有: request, response, chain
     * @param attributes     请求的资源所需要的角色列表
     * @return
     */
    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        // ViewResolver
        if (authentication == null) {
            if (attributes.isEmpty()) {
                return ACCESS_GRANTED;
            }
            return ACCESS_DENIED;
        }
        int result = ACCESS_ABSTAIN;
        Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

        for (ConfigAttribute attribute : attributes) {

            if (this.supports(attribute)) {
                if (attribute.getAttribute().equals(ANONYMOUS)) {
                    result = ACCESS_GRANTED;
                    continue;
                }

                // Attempt to find a matching granted authority
                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        result++;
                    }
                }
            }
        }
        if (result > 0) {
            return ACCESS_DENIED;
        } else {
            return ACCESS_DENIED;
        }
    }

    Collection<? extends GrantedAuthority> extractAuthorities(
            Authentication authentication) {
        return authentication.getAuthorities() == null ? Collections.emptyList() : authentication.getAuthorities();
    }

    @Override
    public boolean supports(Class clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}