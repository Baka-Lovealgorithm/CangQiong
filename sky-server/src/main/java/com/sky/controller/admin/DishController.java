package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Api(tags= "菜品管理")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 添加菜品
     *
     * @param dishDTO
     * @return
     */
    @ApiOperation(value = "新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询菜品")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("分页查询菜品开始{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation(value = "批量删除菜品")
    public Result delete(@RequestParam List<Long>ids) {
        log.info("删除菜品{}", ids);
dishService.deleteBatch(ids);
        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "根据菜品Id查询")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据菜品Id查询{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
/**
 * 修改菜品接口
 * 使用PUT方法请求，接收前端传来的菜品数据
 *
 * @param dishDTO 菜品数据传输对象，包含需要更新的菜品信息
 * @return 返回操作结果，成功时返回成功状态码和消息
 */
@PutMapping
@ApiOperation(value = "修改菜品")
    public Result update(@RequestBody DishDTO dishDTO) {
    log.info("修改菜品{}", dishDTO);
    // 调用服务层方法更新菜品信息，并返回操作结果
    dishService.updateWithFlavor(dishDTO);
    // 此处代码示例中直接返回成功结果，实际应用中应调用相应的服务方法
        return Result.success();
    }
@PostMapping("/status/{status}")
    public Result updateStatus(@RequestParam Long id,@PathVariable Integer status){
dishService.changeStatusById(id,status);
return Result.success();
    }
}

