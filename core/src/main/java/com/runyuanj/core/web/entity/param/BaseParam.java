package com.runyuanj.core.web.entity.param;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.runyuanj.core.web.entity.po.BasePo;
import lombok.Data;

import java.util.Date;

/**
 * 构建sql查询条件
 *
 * @param <T>
 */
@Data
public class BaseParam<T extends BasePo> {

    private Date createTimeStart;
    private Date createTimeEnd;

    public QueryWrapper<T> build() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge(null != this.createTimeStart, "create_time", this.createTimeStart)
                .le(null != this.createTimeEnd, "create_time", this.createTimeEnd);
        return queryWrapper;
    }
}
