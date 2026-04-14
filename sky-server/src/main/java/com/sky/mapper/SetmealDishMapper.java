package com.sky.mapper;

import com.sky.entity.SetmealDish;
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


/**
 * 批量插入套餐和菜品的关联关系
 *
 * @param setmealDishes 套餐和菜品的关联关系列表，每个元素包含套餐ID和菜品ID等信息
 */
    void insert(List<SetmealDish> setmealDishes);
}
