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
public class Menu extends BasePo {
    /**
     * 上级菜单id
     */
    private String parentId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 链接地址
     */
    private String href;
    /**
     * icon
     */
    private String icon;
    /**
     * 排序
     */
    private int orderNum;
    /**
     * 描述
     */
    private String description;
}
