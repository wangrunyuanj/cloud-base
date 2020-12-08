package com.runyuanj.auth.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /* @Autowired
    UserClient userClient; */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //此栗子已在微服务写好查询数据库方法,直接远程调用
        UserDetails userDetails = null;
        // 从org服务获取用户权限信息. userClient.getUserext(username);
        return userDetails;
    }
}