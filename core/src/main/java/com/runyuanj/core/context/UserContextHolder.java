package com.runyuanj.core.context;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * @author runyuan
 */
public class UserContextHolder {

    public static UserDetails userDetails() {
        Assert.notNull(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), "用户未认证");
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 获取上下文中的用户名
     * @param allowAnonymous 是否允许匿名. 允许: 匿名用户返回anonymous, 否则返回null
     * @return
     */
    public static String getUsername(boolean allowAnonymous) {
        String username = userDetails().getUsername();
        if ("anonymous".equalsIgnoreCase(username) && !allowAnonymous) {
            return null;
        }
        return username;
    }

}
