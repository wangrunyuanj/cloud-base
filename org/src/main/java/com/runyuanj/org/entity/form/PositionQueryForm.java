package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseQueryForm;
import com.runyuanj.org.entity.param.ResourceQueryParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class PositionQueryForm extends BaseQueryForm<ResourceQueryParam> {

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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询开始时间必须小于当前日期")
    private Date createdTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询结束时间必须小于当前日期")
    private Date createdTimeEnd;
}
