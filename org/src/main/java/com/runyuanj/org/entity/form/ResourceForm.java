package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseForm;
import com.runyuanj.org.entity.po.Resource;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResourceForm extends BaseForm<Resource> {

    @NotBlank(message = "资源名称不能为空")
    private String name;

    @NotBlank(message = "资源编码不能为空")
    private String code;

    @NotBlank(message = "资源类型不能为空")
    private String type;

    @NotBlank(message = "资源路径不能为空")
    private String url;

    @NotBlank(message = "资源方法不能为空")
    private String method;

    private String description;
}
