package com.runyuanj.core.web.entity.vo;

import com.runyuanj.core.web.entity.po.BasePo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 定义视图对象的公共属性 id
 * <p>
 * 所有用于组装实体类, 用于对外展示的Vo类都继承此类
 */
@Data
@NoArgsConstructor
public class BaseVo<T extends BasePo> implements Serializable {

    private String id;
}
