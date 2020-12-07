package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseQueryForm;
import com.runyuanj.org.entity.param.MenuQueryParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class MenuQueryForm extends BaseQueryForm<MenuQueryParam> {

    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询开始时间必须小于当前日期")
    private Date createdTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询结束时间必须小于当前日期")
    private Date createdTimeEnd;
}
