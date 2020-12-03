package com.runyuanj.core.web.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.FieldFill.INSERT;
import static com.baomidou.mybatisplus.annotation.FieldFill.UPDATE;
import static com.baomidou.mybatisplus.annotation.IdType.ID_WORKER_STR;

/**
 * 定义所有数据库对象的基础属性, 并在插入和更新时自动填充属性
 * 所有实体类对应的数据库表中都包括这些字段
 *
 * 所有表单类都应继承该类
 */
@Data
public class BasePo implements Serializable {

    public final static String DEFAULT_USERNAME = "system";

    @TableId(type = ID_WORKER_STR)
    private String id;

    @TableField(fill = INSERT)
    private String createBy;

    @TableField(fill = INSERT)
    private Date createTime;

    @TableField(fill = UPDATE)
    private String updateBy;

    @TableField(fill = UPDATE)
    private Date updateTime;
}