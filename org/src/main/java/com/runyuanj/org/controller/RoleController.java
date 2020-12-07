package com.runyuanj.org.controller;

import com.runyuanj.common.response.Result;
import com.runyuanj.org.entity.form.RoleForm;
import com.runyuanj.org.entity.form.RoleQueryForm;
import com.runyuanj.org.entity.form.RoleUpdateForm;
import com.runyuanj.org.entity.param.RoleQueryParam;
import com.runyuanj.org.entity.po.Role;
import com.runyuanj.org.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping
    public Result add(@Valid @RequestBody RoleForm roleForm) {
        log.debug("name:{}", roleForm);
        Role role = roleForm.toPo(Role.class);
        return Result.success(roleService.add(role));
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        return Result.success(roleService.delete(id));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody RoleUpdateForm roleUpdateForm) {
        Role role = roleUpdateForm.toPo(id, Role.class);
        return Result.success(roleService.update(role));
    }

    @GetMapping(value = "/{id}")
    public Result get(@PathVariable String id) {
        log.debug("get with id:{}", id);
        return Result.success(roleService.get(id));
    }

    @GetMapping(value = "/all")
    public Result get() {
        return Result.success(roleService.getAll());
    }

    @GetMapping(value = "/user/{userId}")
    public Result query(@PathVariable String userId) {
        log.debug("query with userId:{}", userId);
        return Result.success(roleService.query(userId));
    }

    @PostMapping(value = "/conditions")
    public Result query(@Valid @RequestBody RoleQueryForm roleQueryForm) {
        log.debug("query with name:{}", roleQueryForm);
        return Result.success(roleService.query(roleQueryForm.getPage(), roleQueryForm.toParam(RoleQueryParam.class)));
    }
}