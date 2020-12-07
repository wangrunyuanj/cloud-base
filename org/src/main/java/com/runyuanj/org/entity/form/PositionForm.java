package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseForm;
import com.runyuanj.org.entity.po.Position;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PositionForm extends BaseForm<Position> {

    @NotBlank(message = "职位名称不能为空")
    private String name;

    private String description;
}
