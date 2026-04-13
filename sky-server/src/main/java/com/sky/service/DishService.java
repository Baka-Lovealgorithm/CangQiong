package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
/**
 * 保存带有风味特色的菜品信息
 * 该方法用于将带有风味特色的菜品数据保存到数据库中
 *
 * @param dishDTO 菜品数据传输对象，包含菜品的基本信息及风味特色
 */
    public void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);
}
