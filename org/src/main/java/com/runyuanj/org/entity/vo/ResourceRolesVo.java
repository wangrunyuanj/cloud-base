package com.runyuanj.org.entity.vo;

import com.runyuanj.core.web.entity.vo.BaseVo;
import com.runyuanj.org.entity.po.Resource;
import lombok.Data;

import java.util.List;

/**
 * @author runyu
 */
@Data
public class ResourceRolesVo extends BaseVo<Resource> {

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
     * 请求方式 GET/POST/DELETE/PUT
     */
    private String method;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;

    /**
     * 资源对应的角色信息
     */
    private String roles;

}
