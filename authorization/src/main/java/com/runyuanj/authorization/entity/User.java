package com.runyuanj.authorization.entity;

import com.runyuanj.core.web.entity.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * org服务的User
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false )
@NoArgsConstructor
public class User extends BasePo {
    private String name;
    private String mobile;
    private String username;
    private String password;
    private Boolean enabled;
    /**
     * 账号是否有效
     */
    private Boolean accountNonExpired;
    /**
     * 密码是否有效
     */
    private Boolean credentialsNonExpired;
    private Boolean accountNonLocked;
}