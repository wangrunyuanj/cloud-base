package com.runyuanj.org.entity.po;

import com.runyuanj.core.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource extends BasePo {
    /**
     * 资源编码
     */
    private String code;
    /**
     * 资源类型
     */
    private String type;
    /**
     * 资源对应的url
     */
    private String url;
    /**
     * 方法名
     */
    private String method;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
}
