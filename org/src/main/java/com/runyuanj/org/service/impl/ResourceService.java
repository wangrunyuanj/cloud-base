package com.runyuanj.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.runyuanj.core.utils.BeanUtils;
import com.runyuanj.org.entity.param.ResourceQueryParam;
import com.runyuanj.org.entity.po.Resource;
import com.runyuanj.org.entity.po.Role;
import com.runyuanj.org.entity.po.RoleResource;
import com.runyuanj.org.entity.po.User;
import com.runyuanj.org.entity.vo.ResourceRolesVo;
import com.runyuanj.org.mapper.ResourceMapper;
import com.runyuanj.org.service.IResourceService;
import com.runyuanj.org.service.IRoleResourceService;
import com.runyuanj.org.service.IRoleService;
import com.runyuanj.org.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ResourceService extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {

    @Autowired
    private IRoleResourceService roleResourceService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserService userService;

//    @Autowired
//    private EventSender eventSender;

    @Override
    public boolean add(Resource resource) {
        // eventSender.send(BusConfig.ROUTING_KEY, resource);
        return this.save(resource);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(Resource resource) {
        return this.updateById(resource);
    }

    @Override
    public Resource get(String id) {
        return this.getById(id);
    }

    @Override
    public IPage<Resource> query(Page page, ResourceQueryParam resourceQueryParam) {
        QueryWrapper<Resource> queryWrapper = resourceQueryParam.build();
        queryWrapper.eq(StringUtils.isNotBlank(resourceQueryParam.getName()), "name", resourceQueryParam.getName());
        queryWrapper.eq(StringUtils.isNotBlank(resourceQueryParam.getType()), "type", resourceQueryParam.getType());
        queryWrapper.eq(StringUtils.isNotBlank(resourceQueryParam.getUrl()), "url", resourceQueryParam.getUrl());
        queryWrapper.eq(StringUtils.isNotBlank(resourceQueryParam.getMethod()), "method", resourceQueryParam.getMethod());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Resource> getAll() {
        return this.list();
    }

    @Override
    // @Cached(name = "resource4user::", key = "#username", cacheType = CacheType.BOTH)
    public List<Resource> query(String username) {
        //根据用户名查询到用户所拥有的角色
        User user = userService.getByUniqueId(username);
        List<Role> roles = roleService.query(user.getId());
        //提取用户所拥有角色id列表
        Set<String> roleIds = roles.stream().map(role -> role.getId()).collect(Collectors.toSet());
        //根据角色列表查询到角色的资源的关联关系
        List<RoleResource> roleResources = roleResourceService.queryByRoleIds(roleIds);
        //根据资源列表查询出所有资源对象
        Set<String> resourceIds = roleResources.stream().map(roleResource -> roleResource.getResourceId()).collect(Collectors.toSet());
        //根据resourceId列表查询出resource对象
        return (List<Resource>) this.listByIds(resourceIds);
    }

    @Override
    public List<ResourceRolesVo> getAllResourceRoles() {
        long l = System.currentTimeMillis();
        System.out.println(l);
        List<Resource> resources = this.list();
        List<Role> roles = roleService.getAll();
        List<ResourceRolesVo> rList = new ArrayList<>();
        resources.stream().forEach(resource -> {
            ResourceRolesVo r = new ResourceRolesVo();
            BeanUtils.copyProperties(resource, r);
            // TODO 换成联表查询
            Set<String> roleIds = roleResourceService.queryByResourceId(resource.getId());
            List<String> roleCodeList = new ArrayList<>();
            roles.stream().forEach(role -> {
                roleIds.stream().forEach(roleId -> {
                    if (roleId.equals(role.getId())) {
                        roleCodeList.add(role.getCode());
                    }
                });
            });
            String roleCodes = StringUtils.join(roleCodeList, ",");
            r.setRoles(roleCodes);
            rList.add(r);
        });
        long e = System.currentTimeMillis();
        System.out.println(e - l);
        return rList;
    }
}
