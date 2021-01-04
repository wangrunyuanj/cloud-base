package com.runyuanj.authorization.access;

import com.runyuanj.authorization.service.ResourcePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author runyu
 */
@Slf4j
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private ResourcePermissionService resourcePermissionService;

    public MySecurityMetadataSource(ResourcePermissionService resourcePermissionService) {
        this.resourcePermissionService = resourcePermissionService;
    }

    private static Set<ConfigAttribute> localConfigAttributes = new HashSet<>();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        ConfigAttribute configAttribute = resourcePermissionService.findConfigAttributes(((FilterInvocation) object).getRequest());
        return Arrays.asList(configAttribute);
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return resourcePermissionService.getConfigAttributes();
    }

    /**
     * Indicates whether the {@code SecurityMetadataSource} implementation is able to
     * provide {@code ConfigAttribute}s for the indicated secure object type.
     *
     * @param clazz the class that is being queried
     * @return true if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        log.info("class name: {} /n/t", clazz.getName());
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
