package com.runyuanj.org.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.runyuanj.org.entity.po.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
}