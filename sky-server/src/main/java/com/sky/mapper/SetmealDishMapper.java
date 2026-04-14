package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     *更据多个菜品    id来查询套餐id
     * @param dishids
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishids);



}
