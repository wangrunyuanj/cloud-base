package com.runyuanj.org.entity.param;

import com.runyuanj.core.web.entity.param.BaseParam;
import com.runyuanj.org.entity.po.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuQueryParam extends BaseParam<Menu> {
    private String name;
}
