package com.runyuanj.org.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.runyuanj.core.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class User extends BasePo {
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 描述
     */
    private String description;
    /**
     * 是否有效用户
     */
    private Boolean enabled;

    /**
     * 账号是否未过期
     */
    private Boolean accountNonExpired;

    /**
     * 密码是否未过期
     */
    private Boolean credentialsNonExpired;
    /**
     * 是否未锁定
     */
    private Boolean accountNonLocked;

    /**
     * 对应的角色id列表
     */
    @TableField(exist = false)
    private Set<String> roleIds;

    @TableLogic
    private String deleted = "N";
}
