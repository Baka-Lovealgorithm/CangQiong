package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {
   public void deleteBatch(List<Long> ids) ;

    /**
 * 保存带有风味特色的菜品信息
 * 该方法用于将带有风味特色的菜品数据保存到数据库中
 *
 * @param dishDTO 菜品数据传输对象，包含菜品的基本信息及风味特色
 */
    public void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

/**
 * 根据ID查询菜品及其口味信息
 * @param id 菜品的ID
 * @return DishVO 包含菜品及其口味信息的视图对象
 */
    DishVO getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDTO dishDTO);

/**
 * 根据ID修改状态
 * @param id 要修改起售状态的记录ID
 * @param status 新的状态值
 */
    void changeStatusById(Long id, Integer status);
}
