package com.runyuanj.authorization.entity;

import com.runyuanj.core.web.entity.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * org服务的Role
 *
 * @author Administrator
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Role extends BaseVo {
    private String code;
    private String name;
    private String description;
}
