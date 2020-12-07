package com.runyuanj.org.controller;

import com.runyuanj.common.response.Result;
import com.runyuanj.org.entity.form.PositionForm;
import com.runyuanj.org.entity.param.PositionQueryParam;
import com.runyuanj.org.entity.po.Position;
import com.runyuanj.org.service.IPositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/position")
@Slf4j
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @PostMapping
    public Result add(@Valid @RequestBody PositionForm positionForm) {
        log.debug("name:{}", positionForm);
        Position position = positionForm.toPo(Position.class);
        return Result.success(positionService.add(position));
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        return Result.success(positionService.delete(id));
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody PositionForm positionForm) {
        Position position = positionForm.toPo(Position.class);
        position.setId(id);
        return Result.success(positionService.update(position));
    }

    @GetMapping(value = "/{id}")
    public Result get(@PathVariable String id) {
        log.debug("get with id:{}", id);
        return Result.success(positionService.get(id));
    }

    @GetMapping
    public Result query(@RequestParam String name) {
        log.debug("query with name:{}", name);
        return Result.success(positionService.query(new PositionQueryParam(name)));
    }
}