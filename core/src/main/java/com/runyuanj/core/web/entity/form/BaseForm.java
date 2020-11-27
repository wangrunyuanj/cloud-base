package com.runyuanj.core.web.entity.form;

import com.runyuanj.core.web.entity.po.BasePo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * 定义表单提交对象的公共属性username, 和公共方法 toPo
 * 所有用于表单提交的类都继承该类
 *
 * @param <T>
 */
@Data
@Slf4j
public class BaseForm<T extends BasePo> {

    private String username;

    /**
     * form转化为po, 进行后续处理
     *
     * @param clazz
     * @return
     */
    public T toPo(Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        BeanUtils.copyProperties(this, t);
        return t;
    }

    /**
     * form转化为po, 进行后续处理
     *
     * @param id
     * @param clazz
     * @return
     */
    public T toPo(String id, Class<T> clazz) {
        T t = BeanUtils.instantiateClass(clazz);
        t.setId(id);
        BeanUtils.copyProperties(this, t);
        return t;
    }

}
