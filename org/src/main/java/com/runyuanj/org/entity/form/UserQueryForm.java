package com.runyuanj.org.entity.form;

import com.runyuanj.core.web.entity.form.BaseQueryForm;
import com.runyuanj.org.entity.param.UserQueryParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Past;
import java.util.Date;

@Data
public class UserQueryForm extends BaseQueryForm<UserQueryParam> {

    private String username;

    private String name;

    private String mobile;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询开始时间必须小于当前日期")
    private Date createdTimeStart;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past(message = "查询结束时间必须小于当前日期")
    private Date createdTimeEnd;
}
