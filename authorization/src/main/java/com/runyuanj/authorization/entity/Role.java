package com.runyuanj.authorization.entity;

import com.runyuanj.core.web.entity.po.BasePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * org服务的Role
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false )
@NoArgsConstructor
public class Role extends BasePo {
    private String code;
    private String name;
    private String description;
}
