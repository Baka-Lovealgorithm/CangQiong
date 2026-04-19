package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
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

/**
 * 根据套餐ID删除相关数据
 * @param id 套餐ID，用于标识需要删除的套餐
 */
@Delete("delete from setmeal_dish where setmeal_id = #{id}")
    void deleteBySetmealId(Long id);

/**
 * 根据套餐ID查询套餐和菜品的关联信息
 *
 * @param id 套餐ID，用于查询与该套餐关联的所有菜品信息
 * @return 返回一个SetmealDish对象的列表，包含与指定套餐ID关联的所有菜品信息
 */
    List<SetmealDish> getBySetmealId(Long id);

    void deleteBySetmealIds(List<Long> setmealIds);
}
