package com.runyuanj.org.entity.param;

import com.runyuanj.core.web.entity.param.BaseParam;
import com.runyuanj.org.entity.po.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleQueryParam extends BaseParam<Role> {
    private String code;
    private String name;
}
