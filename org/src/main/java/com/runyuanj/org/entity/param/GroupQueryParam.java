package com.runyuanj.org.entity.param;

import com.runyuanj.core.web.entity.param.BaseParam;
import com.runyuanj.org.entity.po.Group;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupQueryParam extends BaseParam<Group> {
    private String name;
}
