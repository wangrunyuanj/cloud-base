package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseQueryForm;
import com.runyuanj.org.entity.param.GroupQueryParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class GroupQueryForm extends BaseQueryForm<GroupQueryParam> {

    @NotBlank(message = "用户组名称不能为空")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询开始时间必须小于当前日期")
    private Date createdTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询结束时间必须小于当前日期")
    private Date createdTimeEnd;
}
