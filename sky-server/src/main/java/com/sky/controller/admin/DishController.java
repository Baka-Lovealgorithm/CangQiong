package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/dish")
@Api(tags= "菜品管理")
@Slf4j
public class DishController {
    @Autowired
   private DishService dishService;
    /**
     * 添加菜品
     * @param dishDTO
     * @return
     */
    @ApiOperation(value = "新增菜品")
    @PostMapping
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }
@GetMapping("/page")
@ApiOperation(value = "分页查询菜品")
    public Result<PageResult>page(DishPageQueryDTO dishPageQueryDTO) {
log.info("分页查询菜品开始{}",dishPageQueryDTO);
PageResult pageResult=dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    }

