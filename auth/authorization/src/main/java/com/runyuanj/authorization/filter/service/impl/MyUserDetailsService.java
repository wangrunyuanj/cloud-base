package com.runyuanj.authorization.filter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.runyuanj.authorization.entity.MyUser;
import com.runyuanj.authorization.entity.Role;
import com.runyuanj.authorization.entity.User;
import com.runyuanj.authorization.service.OrgServiceFeign;
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
import java.util.List;

import static java.util.stream.Collectors.toSet;

/**
 * UserDetailsService: Spring Security的核心接口, 获取用户的指定信息.
 * 实现Spring Security的UserDetailService,通过username构建UserDetails对象
 *
 * @author Administrator
 */
@Slf4j
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private OrgServiceFeign orgServiceFeign;

    /**
     * 加载用户信息
     *
     * @param uniqueId 用户名或手机号邮箱号, 用来表示用户身份的唯一标识
     * @return UserDetails 构建Authentication对象必须的关键信息，可以自定义，可能需要访问DB得到. 这里使用Spring Security默认的实现User.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String uniqueId) throws UsernameNotFoundException {
        JSONObject json = orgServiceFeign.getUserByUniqueId(uniqueId);
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
        // 将数据库的User组装成Spring Security默认的实现类User
        return new MyUser(
                user.getId(),
                uniqueId,
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                // 用户的授权信息, 通常用角色表示
                this.obtainGrantedAuthorities(user));
    }

    /**
     * 获得登录者所有角色的授权信息, 包装为GrantedAuthority集合
     *
     * @param user
     * @return
     */
    protected Collection<? extends GrantedAuthority> obtainGrantedAuthorities(User user) {
        // 查询user的所有角色,
        JSONObject jsonObject = orgServiceFeign.queryRolesByUserId(user.getId());
        if (Result.isJsonSuccess(jsonObject)) {
            log.info("result: {}", jsonObject.toJSONString());
            JSONArray data = jsonObject.getJSONArray("data");
            if (data != null) {
                List<Role> rolesList = data.toJavaList(Role.class);
                if (!rolesList.isEmpty()) {
                    return rolesList.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(toSet());
                }
            }
        }
        return null;
    }
}
