package com.runyuanj.org.entity.param;

import com.runyuanj.core.web.entity.param.BaseParam;
import com.runyuanj.org.entity.po.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryParam extends BaseParam<User> {
    private String name;
    private String mobile;
    private String username;
}
