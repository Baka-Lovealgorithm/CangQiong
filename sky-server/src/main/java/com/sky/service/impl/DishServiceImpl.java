package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private SetmealMapper setmealMapper;
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

dishMapper.insert(dish);
Long dishId=dish.getId();
List<DishFlavor> dishFlavors=dishDTO.getFlavors();
    if(dishFlavors!=null&&dishFlavors.size()>0){
dishFlavors.forEach(dishFlavor -> {dishFlavor.setDishId(dishId);});
        dishFlavorMapper.insertBatch(dishFlavors);
    }
    }

    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //产看是否有在售菜品
        List<Long> setmealDishIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealDishIds != null && setmealDishIds.size() > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //查看是否有关联套餐
        for(Long id:ids){
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteByDishId(id);
        }
        //删除菜品


    }

/**
 * 根据ID查询菜品信息，并返回包含口味数据的菜品对象
 * @param id 菜品的ID
 * @return DishVO 包含菜品及口味信息的视图对象，如果未找到则返回null
 */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        Dish dish=dishMapper.getById(id);
        List<DishFlavor> dishFlavors=dishFlavorMapper.getByDishId(id);
        DishVO dishVO=new DishVO();
        BeanUtils.copyProperties(dish,dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    @Override
    public void updateWithFlavor(DishDTO dishDTO) {
        //修改基本信息
        Dish dish=new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.update(dish);
        //删除口味信息
        dishFlavorMapper.deleteByDishId(dishDTO.getId());

        //新增口味信息
        List<DishFlavor> dishFlavors=dishDTO.getFlavors();
        if(dishFlavors!=null&&dishFlavors.size()>0){
            dishFlavors.forEach(dishFlavor -> {dishFlavor.setDishId(dishDTO.getId());});
            dishFlavorMapper.insertBatch(dishFlavors);
        }

    }

    @Override
    public void changeStatusById(Long id, Integer status) {
        Dish dish =Dish.builder().id(id).status(status).build();
        dishMapper.update(dish);

        //如多状态表示停售，需要将对应的套餐也改为停售
        if(status.equals(StatusConstant.DISABLE)){
            List<Long> dishIds=new ArrayList<>();
            dishIds.add(id);
            List<Long> setmealIds=setmealDishMapper.getSetmealIdsByDishIds(dishIds);

            if(setmealIds!=null&&setmealIds.size()>0) {
                for (Long setmealId : setmealIds) {
                    Setmeal setmeal = Setmeal.builder().id(setmealId).status(status).build();
                    setmealMapper.update(setmeal);
                }
            }
        }
    }

    @Override
    public List<DishVO> selectByCategoryId(Long categoryId) {
List<DishVO> dishVOS=dishMapper.SelectdishByCategoryId(categoryId);
        return dishVOS;
    }
}
