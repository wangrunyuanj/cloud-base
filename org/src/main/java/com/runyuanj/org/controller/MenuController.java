package com.runyuanj.org.controller;

import com.runyuanj.common.response.Result;
import com.runyuanj.org.entity.form.MenuForm;
import com.runyuanj.org.entity.form.MenuQueryForm;
import com.runyuanj.org.entity.param.MenuQueryParam;
import com.runyuanj.org.entity.po.Menu;
import com.runyuanj.org.service.IMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/menu")
@Slf4j
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @PostMapping
    public Result add(@Valid @RequestBody MenuForm menuForm) {
        log.debug("name:{}", menuForm);
        Menu menu = menuForm.toPo(Menu.class);
        return Result.success(menuService.add(menu));
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        return Result.success(menuService.delete(id));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody MenuForm menuForm) {
        Menu menu = menuForm.toPo(Menu.class);
        menu.setId(id);
        return Result.success(menuService.update(menu));
    }

    @GetMapping(value = "/{id}")
    public Result get(@PathVariable String id) {
        log.debug("get with id:{}", id);
        return Result.success(menuService.get(id));
    }

    @GetMapping
    public Result query(@RequestParam String name) {
        log.debug("query with name:{}", name);
        MenuQueryParam menuQueryParam = new MenuQueryParam(name);
        return Result.success(menuService.query(menuQueryParam));
    }

    @PostMapping(value = "/conditions")
    public Result search(@Valid @RequestBody MenuQueryForm menuQueryForm) {
        log.debug("search with menuQueryForm:{}", menuQueryForm);
        return Result.success(menuService.query(menuQueryForm.toParam(MenuQueryParam.class)));
    }

    @GetMapping(value = "/parent/{id}")
    public Result search(@PathVariable String id) {
        log.debug("query with parent id:{}", id);
        return Result.success(menuService.queryByParentId(id));
    }
}