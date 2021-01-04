package com.runyuanj.authorization.access;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 角色等级
 *
 */
@Component
public class MyRoleHierarchy implements RoleHierarchy {
    /**
     * Returns an array of all reachable authorities.
     * <p>
     * Reachable authorities are the directly assigned authorities plus all authorities
     * that are (transitively) reachable from them in the role hierarchy.
     * <p>
     * Example:<br>
     * Role hierarchy: ROLE_A &gt; ROLE_B &gt; ROLE_C.<br>
     * Directly assigned authority: ROLE_A.<br>
     * Reachable authorities: ROLE_A, ROLE_B, ROLE_C.
     *
     * @param authorities - List of the directly assigned authorities.
     * @return List of all reachable authorities given the assigned authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return null;
    }
}
