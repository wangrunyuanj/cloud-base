package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseForm;
import com.runyuanj.org.entity.po.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Data
public class UserUpdateForm extends BaseForm<User> {

    @Length(min = 3, max = 20, message = "用户名长度在3到20个字符")
    private String username;

    @Length(min = 5, max = 20, message = "密码长度在5到20个字符")
    private String password;

    private String mobile;

    private String name;

    private String description;

    private Set<String> roleIds;

    private Boolean enabled = true;

    private Boolean accountNonExpired = true;

    private Boolean credentialsNonExpired = true;

    private Boolean accountNonLocked = true;
}
