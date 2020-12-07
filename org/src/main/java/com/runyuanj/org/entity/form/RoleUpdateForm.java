package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseForm;
import com.runyuanj.org.entity.po.Role;
import lombok.Data;

import java.util.Set;

@Data
public class RoleUpdateForm extends BaseForm<Role> {

    private String code;

    private String name;

    private String description;

    private Set<String> resourceIds;

}
