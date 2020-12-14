package com.runyuanj.authorization.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.runyuanj.authorization.entity.Role;
import com.runyuanj.authorization.entity.User;
import com.runyuanj.authorization.service.ServiceFeign;
import com.runyuanj.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ServiceFeign serviceFeign;


    @Override
    public UserDetails loadUserByUsername(String uniqueId) throws UsernameNotFoundException {
        JSONObject json = serviceFeign.getUserByUniqueId(uniqueId);
        User user = null;
        if (Result.isJsonSuccess(json)) {
            JSONObject data = json.getJSONObject("data");
            if (data != null) {
                user = data.toJavaObject(User.class);
            }
        }
        if (user == null) {
            log.info("load empty User By Username: {}", uniqueId);
        }
        log.info("load user by username :{}", user.toString());
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                this.obtainGrantedAuthorities(user));
    }

    /**
     * 获得登录者所有角色的权限集合.
     *
     * @param user
     * @return
     */
    protected Collection<? extends GrantedAuthority> obtainGrantedAuthorities(User user) {
        JSONObject jsonObject = serviceFeign.queryRolesByUserId(user.getId());
        if (Result.isJsonSuccess(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            if (data != null) {
                HashSet<Role> rolesSet = data.toJavaObject(HashSet.class);
                if (!rolesSet.isEmpty()) {
                    log.info("user:{},roles:{}", user.getUsername(), rolesSet);
                    return rolesSet.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());
                }
            }
        }
        return null;
    }
}
