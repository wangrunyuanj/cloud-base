package com.runyuanj.core.web.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.runyuanj.core.context.UserContextHolder;
import com.runyuanj.core.web.entity.po.BasePo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Date;
import java.time.ZonedDateTime;

public class PoMetaObjectHandler implements MetaObjectHandler {

    private String getCurrentUsername() {
        return StringUtils.defaultIfBlank(UserContextHolder.getInstance().getUsername(), BasePo.DEFAULT_USERNAME);
    }

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setInsertFieldValByName("createBy", getCurrentUsername(), metaObject);
        this.setInsertFieldValByName("createTime", Date.from(ZonedDateTime.now().toInstant()), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName("updatedBy", getCurrentUsername(), metaObject);
        this.setUpdateFieldValByName("updatedTime", java.util.Date.from(ZonedDateTime.now().toInstant()), metaObject);
    }
}
