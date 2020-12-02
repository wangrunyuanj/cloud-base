package com.runyuanj.auth.server.events;

import com.runyuanj.auth.server.service.IResourceService;
import com.runyuanj.core.auth.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 管理缓存信息. 更新本地缓存
 * 1. 清除所有本地缓存
 * 2. 立即更新本地缓存
 */
@Component
@Slf4j
public class BusReceiver {

    @Autowired
    private IResourceService resourceService;

    public void handleMessage(Resource resource) {
        log.info("Received Message:<{}>", resource);
    }
}
