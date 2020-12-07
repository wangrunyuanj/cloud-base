package com.runyuanj.org.entity.po;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.runyuanj.core.web.entity.po.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Position extends BasePo {
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    @TableLogic
    private String deleted = "N";
}
