package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseForm;
import com.runyuanj.org.entity.po.Menu;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MenuForm extends BaseForm<Menu> {

    @NotBlank(message = "菜单父id不能为空")
    private String parentId;

    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @NotBlank(message = "菜单类型不能为空")
    private String type;

    @NotBlank(message = "菜单路径不能为空")
    private String href;

    private String icon;

    private String orderNum;

    private String description;
}
