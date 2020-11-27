package com.runyuanj.core.web.entity.form;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.runyuanj.core.web.entity.param.BaseParam;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * 定义表单提交查询对象的分页查询基础属性, current 和 size
 * 所有表单查询类都继承该类
 *
 * @param <P>
 */
@Data
@Slf4j
public class BaseQueryForm<P extends BaseParam> extends BaseForm {

    /**
     * 分页查询参数, 当前页数
     */
    private long current = 1;

    /**
     * 分页查询参数, 每页显示数量
     */
    private long size = 10;

    public P toParam(Class<P> clazz) {
        P p = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, p);
        return p;
    }

    /**
     * 从form中获取page参数, 用于分页查询
     */
    public Page getPage() {
        return new Page(this.current, this.size);
    }
}
