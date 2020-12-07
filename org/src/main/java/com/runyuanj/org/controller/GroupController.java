package com.runyuanj.org.controller;

import com.runyuanj.common.response.Result;
import com.runyuanj.org.entity.form.GroupForm;
import com.runyuanj.org.entity.form.GroupQueryForm;
import com.runyuanj.org.entity.param.GroupQueryParam;
import com.runyuanj.org.entity.po.Group;
import com.runyuanj.org.service.IGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/group")
@Slf4j
public class GroupController {

    @Autowired
    private IGroupService groupService;

    @PostMapping
    public Result add(@Valid @RequestBody GroupForm groupForm) {
        log.debug("name:{}", groupForm);
        return Result.success(groupService.add(groupForm.toPo(Group.class)));
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        return Result.success(groupService.delete(id));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody GroupForm groupForm) {
        Group group = groupForm.toPo(Group.class);
        group.setId(id);
        return Result.success(groupService.update(group));
    }

    @GetMapping(value = "/{id}")
    public Result get(@PathVariable String id) {
        log.debug("get with id:{}", id);
        return Result.success(groupService.get(id));
    }

    @GetMapping
    public Result query(@RequestParam String name) {
        log.debug("query with name:{}", name);
        GroupQueryParam groupQueryParam = new GroupQueryParam();
        groupQueryParam.setName(name);
        return Result.success(groupService.query(groupQueryParam));
    }

    @PostMapping(value = "/conditions")
    public Result search(@Valid @RequestBody GroupQueryForm groupQueryForm) {
        log.debug("search with groupQueryForm:{}", groupQueryForm);
        return Result.success(groupService.query(groupQueryForm.toParam(GroupQueryParam.class)));
    }

    @GetMapping(value = "/parent/{id}")
    public Result search(@PathVariable String id) {
        log.debug("query with parent id:{}", id);
        return Result.success(groupService.queryByParentId(id));
    }
}