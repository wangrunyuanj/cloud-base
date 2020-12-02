package com.runyuanj.core.auth;

import com.runyuanj.core.web.entity.po.BasePo;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 权限资源信息
 *
 * @author Administrator
 */
@Data
@NoArgsConstructor
public class Resource extends BasePo {
    private String code;
    private String name;
    private String type;
    private String url;
    private String method;
    private String description;
}
