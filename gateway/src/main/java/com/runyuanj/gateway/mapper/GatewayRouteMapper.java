package com.runyuanj.gateway.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.runyuanj.gateway.entity.po.GatewayRoute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface GatewayRouteMapper extends BaseMapper<GatewayRoute> {

}