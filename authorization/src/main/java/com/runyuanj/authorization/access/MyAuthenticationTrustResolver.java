package com.runyuanj.authorization.access;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;

public class MyAuthenticationTrustResolver implements AuthenticationTrustResolver {
    /**
     * Indicates whether the passed <code>Authentication</code> token represents an
     * anonymous user. Typically the framework will call this method if it is trying to
     * decide whether an <code>AccessDeniedException</code> should result in a final
     * rejection (i.e. as would be the case if the principal was non-anonymous/fully
     * authenticated) or direct the principal to attempt actual authentication (i.e. as
     * would be the case if the <code>Authentication</code> was merely anonymous).
     *
     * @param authentication to test (may be <code>null</code> in which case the method
     *                       will always return <code>false</code>)
     * @return <code>true</code> the passed authentication token represented an anonymous
     * principal, <code>false</code> otherwise
     */
    @Override
    public boolean isAnonymous(Authentication authentication) {
        return false;
    }

    /**
     * Indicates whether the passed <code>Authentication</code> token represents user that
     * has been remembered (i.e. not a user that has been fully authenticated).
     * <p>
     * The method is provided to assist with custom <code>AccessDecisionVoter</code>s and
     * the like that you might develop. Of course, you don't need to use this method
     * either and can develop your own "trust level" hierarchy instead.
     *
     * @param authentication to test (may be <code>null</code> in which case the method
     *                       will always return <code>false</code>)
     * @return <code>true</code> the passed authentication token represented a principal
     * authenticated using a remember-me token, <code>false</code> otherwise
     */
    @Override
    public boolean isRememberMe(Authentication authentication) {
        return false;
    }
}
