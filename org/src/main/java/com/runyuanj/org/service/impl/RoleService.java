package com.runyuanj.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.runyuanj.org.entity.param.RoleQueryParam;
import com.runyuanj.org.entity.po.Role;
import com.runyuanj.org.exception.RoleNotFoundException;
import com.runyuanj.org.mapper.RoleMapper;
import com.runyuanj.org.service.IRoleResourceService;
import com.runyuanj.org.service.IRoleService;
import com.runyuanj.org.service.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class RoleService extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleResourceService roleResourceService;

    @Override
    public boolean add(Role role) {
        boolean isSuccess = this.save(role);
        roleResourceService.saveBatch(role.getId(), role.getResourceIds());
        return isSuccess;
    }

    @Override
    public boolean delete(String id) {
        roleResourceService.removeByRoleId(id);
        return this.removeById(id);
    }

    @Override
    public boolean update(Role role) {
        boolean isSuccess = this.updateById(role);
        roleResourceService.saveBatch(role.getId(), role.getResourceIds());
        return isSuccess;
    }

    @Override
    public Role get(String id) {
        Role role = this.getById(id);
        if (Objects.isNull(role)) {
            throw new RoleNotFoundException("role not found with id:" + id);
        }
        role.setResourceIds(roleResourceService.queryByRoleId(id));
        return role;
    }

    @Override
    public List<Role> getAll() {
        return this.list();
    }

    @Override
    public List<Role> query(String userId) {
        Set<String> roleIds = userRoleService.queryByUserId(userId);
        return (List<Role>) this.listByIds(roleIds);
    }

    @Override
    public IPage<Role> query(Page page, RoleQueryParam roleQueryParam) {
        QueryWrapper<Role> queryWrapper = roleQueryParam.build();
        queryWrapper.eq(StringUtils.isNotBlank(roleQueryParam.getName()), "name", roleQueryParam.getName());
        queryWrapper.eq(StringUtils.isNotBlank(roleQueryParam.getCode()), "code", roleQueryParam.getCode());
        return this.page(page, queryWrapper);
    }


}
