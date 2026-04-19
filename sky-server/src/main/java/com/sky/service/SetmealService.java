package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
/**
 * 保存套餐信息的方法
 * @param setmealDTO 套餐数据传输对象，包含套餐的详细信息
 */
    public void save(SetmealDTO setmealDTO);

/**
 * 分页查询套餐信息
 * @param setmealPageQueryDTO 套餐分页查询条件数据传输对象
 * 用于接收前端传递的分页查询参数，包括页码、每页大小、查询条件等
 */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);


/**
 * 这是一个方法，用于控制套餐的起售或停售状态
 * 方法接收两个参数：状态标识和套餐ID
 * 状态参数(status)决定是启用套餐还是禁用套餐
 * ID参数(id)指定要操作的套餐
 */
    void startOrStop(int status, Long id); // 方法声明：startOrStop，接收int和Long类型参数，无返回值

/**
 * 更新套餐信息的方法
 * @param setmealDTO 套餐数据传输对象，包含需要更新的套餐信息
 */
    void update(SetmealDTO setmealDTO);

    SetmealVO getById(Long id);

    void deleteBatch(List<Long> ids);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}
