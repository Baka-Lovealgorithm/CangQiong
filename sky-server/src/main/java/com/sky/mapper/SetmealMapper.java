package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);


@AutoFill(OperationType.UPDATE)
    void update(Setmeal setmeal);

/**
 * 向数据库中插入一条套餐记录
 * @param setmeal 套餐对象，包含需要插入的套餐信息
 */
@AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);
}
