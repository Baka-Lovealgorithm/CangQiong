package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Employee;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
setmealMapper.insert(setmeal);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        Long setmealid=setmeal.getId();

        if(setmealDishes!=null&&setmealDishes.size()>0){
            for(SetmealDish setmealDish:setmealDishes){
                setmealDish.setSetmealId(setmealid);
            }
        setmealDishMapper.insert(setmealDishes);
        }
    }

/**
 * 分页查询套餐信息
 * @param setmealPageQueryDTO 套餐分页查询条件数据传输对象
 */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page=setmealMapper.pageQuery(setmealPageQueryDTO);
        long total = page.getTotal();
        List<SetmealVO> records = page.getResult();
        return new PageResult(total, records);
    }


    @Override
    public void startOrStop(int status, Long id) {
        Setmeal setmeal=new Setmeal();
        setmeal.setStatus(status);
        setmeal.setId(id);
setmealMapper.update(setmeal);
    }

    @Override
    public SetmealVO getById(Long id) {
        //搜索setmeal
        SetmealVO setmealVO=setmealMapper.getById(id);
        //填充categoryName
        setmealVO.setCategoryName(setmealMapper.getCategoryNameById(setmealVO.getCategoryId()));
        //填充setmealdish
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    @Override
    public void update(SetmealDTO setmealDTO) {
        //修改setmeal表的基本信息
        Setmeal setmeal=new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        Long setmealid=setmeal.getId();
        log.info("setmealid:{}",setmealid);
        log.info("setmealDTOid:{}",setmealDTO.getId());
        //修改菜品对应关系
        //先删除setmealdish中的关系
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        //再添加新的关系
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        if(setmealDishes!=null&&setmealDishes.size()>0){
            for(SetmealDish setmealDish:setmealDishes){
                setmealDish.setSetmealId(setmealid);
            }
            setmealDishMapper.insert(setmealDishes);
        }

    }
@Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        if(ids!=null&&ids.size()>0) {
            for (Long id : ids) {
                SetmealVO setmealVO=setmealMapper.getById(id);
                if(setmealVO.getStatus()== StatusConstant.ENABLE) {
                    throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
                }
            }
        }
        //判断是否有套餐处于在售状态
        setmealMapper.deleteBatch(ids);
        //删除套餐
        setmealDishMapper.deleteBySetmealIds(ids);
    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
