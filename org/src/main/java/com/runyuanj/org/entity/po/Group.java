package com.runyuanj.org.entity.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.runyuanj.core.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("groups")
public class Group extends BasePo {
    /**
     * 名称
     */
    private String name;
    /**
     * 上级组id
     */
    private String parentId;
    /**
     * 描述
     */
    private String description;
    /**
     * mysql逻辑删除
     */
    @TableLogic
    private String deleted = "N";
}
