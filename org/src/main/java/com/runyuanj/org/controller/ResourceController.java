package com.runyuanj.org.controller;

import com.runyuanj.common.response.Result;
import com.runyuanj.org.entity.form.ResourceForm;
import com.runyuanj.org.entity.form.ResourceQueryForm;
import com.runyuanj.org.entity.param.ResourceQueryParam;
import com.runyuanj.org.entity.po.Resource;
import com.runyuanj.org.service.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/resource")
@Slf4j
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @PostMapping
    public Result add(@Valid @RequestBody ResourceForm resourceForm) {
        log.debug("name:{}", resourceForm);
        Resource resource = resourceForm.toPo(Resource.class);
        return Result.success(resourceService.add(resource));
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        return Result.success(resourceService.delete(id));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody ResourceForm resourceForm) {
        Resource resource = resourceForm.toPo(id, Resource.class);
        return Result.success(resourceService.update(resource));
    }

    @GetMapping(value = "/{id}")
    public Result get(@PathVariable String id) {
        log.debug("get with id:{}", id);
        return Result.success(resourceService.get(id));
    }

    @GetMapping(value = "/user/{username}")
    public Result queryByUsername(@PathVariable String username) {
        log.debug("query with username:{}", username);
        return Result.success(resourceService.query(username));
    }

    @GetMapping(value = "/all")
    public Result queryAll() {
        return Result.success(resourceService.getAll());
    }

    @PostMapping(value = "/conditions")
    public Result query(@Valid @RequestBody ResourceQueryForm resourceQueryForm) {
        log.debug("query with name:{}", resourceQueryForm);
        return Result.success(resourceService.query(resourceQueryForm.getPage(), resourceQueryForm.toParam(ResourceQueryParam.class)));
    }
}