package com.runyuanj.org.controller;

import com.runyuanj.common.response.Result;
import com.runyuanj.org.entity.form.UserForm;
import com.runyuanj.org.entity.form.UserQueryForm;
import com.runyuanj.org.entity.form.UserUpdateForm;
import com.runyuanj.org.entity.param.UserQueryParam;
import com.runyuanj.org.entity.po.User;
import com.runyuanj.org.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public Result add(@Valid @RequestBody UserForm userForm) {
        log.debug("name:{}", userForm);
        User user = userForm.toPo(User.class);
        return Result.success(userService.add(user));
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        return Result.success(userService.delete(id));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody UserUpdateForm userUpdateForm) {
        User user = userUpdateForm.toPo(User.class);
        user.setId(id);
        return Result.success(userService.update(user));
    }

    @GetMapping(value = "/{id}")
    public Result get(@PathVariable String id) {
        log.debug("get with id:{}", id);
        return Result.success(userService.get(id));
    }

    @GetMapping
    public Result query(@RequestParam String uniqueId) {
        log.debug("query with username or mobile:{}", uniqueId);
        return Result.success(userService.getByUniqueId(uniqueId));
    }

    @PostMapping(value = "/conditions")
    public Result search(@Valid @RequestBody UserQueryForm userQueryForm) {
        log.debug("search with userQueryForm:{}", userQueryForm);
        return Result.success(userService.query(userQueryForm.getPage(), userQueryForm.toParam(UserQueryParam.class)));
    }
}