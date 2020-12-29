package com.runyuanj.authorization.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 保存在SecurityContextHolder, 相比于User, 额外补充其他信息
 *
 * @author runyu
 */
public class MyUser extends User {

    private String userId;
    private String extend;

    /**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     *
     * @param username
     * @param password
     * @param authorities
     */
    public MyUser(String userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    /**
     *
     * @param username
     * @param password
     * @param enabled
     * @param accountNonExpired
     * @param credentialsNonExpired
     * @param accountNonLocked
     * @param authorities
     */
    public MyUser(String userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
