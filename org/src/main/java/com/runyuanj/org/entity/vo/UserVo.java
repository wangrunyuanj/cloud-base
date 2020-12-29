package com.runyuanj.org.entity.vo;

import com.runyuanj.core.web.entity.vo.BaseVo;
import com.runyuanj.org.entity.po.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserVo extends BaseVo<User> {

    private String name;
    private String mobile;
    private String username;
    private String description;
    private String deleted;
    private Set<String> roleIds;
    private String createdBy;
    private String updatedBy;
    private Date createdTime;
    private Date updatedTime;
    public UserVo(User user) {
        BeanUtils.copyProperties(user, this);
    }
}
