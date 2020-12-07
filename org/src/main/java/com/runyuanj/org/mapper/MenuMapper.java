package com.runyuanj.org.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.runyuanj.org.entity.po.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
}