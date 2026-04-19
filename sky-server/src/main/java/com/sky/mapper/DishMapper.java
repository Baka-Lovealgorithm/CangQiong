package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 添加菜品
     * @param dish
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);
Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
@Select("select * from dish where id = #{id}")
    Dish getById(Long id);
@Delete("delete from dish where id = #{id}")
    void deleteById(Long id);
/**
 * 更新菜品信息的方法
 * @AutoFill 注解表示此方法会自动填充某些字段，如更新时间、更新人等
 * OperationType.UPDATE 指定了操作类型为更新操作
 * @param dish 包含要更新的菜品信息的对象
 */
@AutoFill(OperationType.UPDATE)
    void update(Dish dish);  // 接收一个Dish类型的参数，用于更新菜品信息

    List<DishVO> SelectdishByCategoryId(Long categoryId);

    /**
     * 动态条件查询菜品
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);
}
