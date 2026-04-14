package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 插入口味
     * @param flavors
     */
    public void insertBatch(List<DishFlavor> flavors);
@Delete("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

/**
 * 根据菜品ID查询对应的口味列表
 *
 * @param dishId 菜品ID，用于查询该菜品的口味信息
 * @return 返回与指定菜品ID关联的口味列表，如果没有找到则返回空列表
 */
@Select("select * from dish_flavor where dish_id = #{dishId}")
List<DishFlavor> getByDishId(Long dishId);
}
