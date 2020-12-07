package com.runyuanj.org.service;

import com.runyuanj.org.entity.param.PositionQueryParam;
import com.runyuanj.org.entity.po.Position;

import java.util.List;

public interface IPositionService {
    /**
     * 获取职位
     *
     * @param id
     * @return
     */
    Position get(String id);

    /**
     * 新增职位
     *
     * @param position
     * @return
     */
    boolean add(Position position);

    /**
     * 查询职位
     *
     * @return
     */
    List<Position> query(PositionQueryParam positionQueryParam);

    /**
     * 更新职位信息
     *
     * @param position
     */
    boolean update(Position position);

    /**
     * 根据id删除职位
     *
     * @param id
     */
    boolean delete(String id);
}
