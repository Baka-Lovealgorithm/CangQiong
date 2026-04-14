package com.sky.service;

import com.sky.dto.SetmealDTO;

public interface SetmealService {
/**
 * 保存套餐信息的方法
 * @param setmealDTO 套餐数据传输对象，包含套餐的详细信息
 */
    public void save(SetmealDTO setmealDTO);
}
