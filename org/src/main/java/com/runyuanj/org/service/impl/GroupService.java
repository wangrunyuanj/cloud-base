package com.runyuanj.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.runyuanj.org.entity.param.GroupQueryParam;
import com.runyuanj.org.entity.po.Group;
import com.runyuanj.org.mapper.GroupMapper;
import com.runyuanj.org.service.IGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GroupService extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    @Override
    public boolean add(Group group) {
        return this.save(group);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean update(Group group) {
        return this.updateById(group);
    }

    @Override
    public Group get(String id) {
        return this.getById(id);
    }

    @Override
    public List<Group> query(GroupQueryParam groupQueryParam) {
        QueryWrapper<Group> queryWrapper = groupQueryParam.build();
        queryWrapper.eq("name", groupQueryParam.getName());
        return this.list(queryWrapper);
    }

    @Override
    public List<Group> queryByParentId(String id) {
        return this.list(new QueryWrapper<Group>().eq("parent_id", id));
    }
}
