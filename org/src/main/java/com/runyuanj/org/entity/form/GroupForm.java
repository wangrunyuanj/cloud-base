package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseForm;
import com.runyuanj.org.entity.po.Group;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class GroupForm extends BaseForm<Group> {

    @NotBlank(message = "用户组父id不能为空")
    private String parentId;

    @NotBlank(message = "用户组名称不能为空")
    private String name;

    private String description;
}
