package com.runyuanj.auth.server.service.impl;

import com.runyuanj.auth.server.service.IResourceService;
import com.runyuanj.core.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Service
@Slf4j
public class ResourceService implements IResourceService {
    /**
     * 获取此url, method对应的权限资源信息
     *
     * @param authRequest
     * @return
     */
    @Override
    public ConfigAttribute findConfigAttributesByUrl(HttpServletRequest authRequest) {

        return null;
    }

    /**
     * 根据用户名查询资源权限信息
     *
     * @param username
     * @return
     */
    @Override
    public Set<Resource> queryByUsername(String username) {

        // 构建缓存key
        String cacheKey = getResourceCacheKey(username);
        return null;
    }

    private String getResourceCacheKey(String username) {
        return null;
    }
}
