package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags="套餐管理")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;


@PostMapping
@ApiOperation("value = 新增套餐")
@CacheEvict(value = "setmealCache",allEntries = true,key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetmealDTO setmealDTO)
    {
        log.info("新增套餐开始{}",setmealDTO);
setmealService.save(setmealDTO);
        return Result.success();
    }
    @GetMapping("/page")
    @ApiOperation("分页查询套餐")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO)
    {
        log.info("分页查询套餐开始{}",setmealPageQueryDTO);
        PageResult pageResult= setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }
@PostMapping("/status/{status}")
@ApiOperation("修改套餐状态")
@CacheEvict(value = "setmealCache",allEntries = true)
public Result startOrStop(@PathVariable int status, Long id)
{
    log.info("修改套餐状态开始{}",status);
    setmealService.startOrStop(status,id);
    return Result.success();
}
@PutMapping
@ApiOperation("修改套餐")
@CacheEvict(value = "setmealCache",allEntries = true)
public Result update(@RequestBody SetmealDTO setmealDTO)
{
    log.info("修改套餐开始{}",setmealDTO);
    setmealService.update(setmealDTO);
    return Result.success();
}

@GetMapping("{id}")
    @ApiOperation("查询套餐")
public Result<SetmealVO> getById(@PathVariable Long id)
{
    log.info("查询套餐开始{}",id);
    SetmealVO setmealVO=setmealService.getById(id);
    return Result.success(setmealVO);
}

@DeleteMapping
@ApiOperation("删除套餐")
@CacheEvict(value = "setmealCache",allEntries = true)
public Result deleteBatch(@RequestParam List<Long> ids)
{
    log.info("删除套餐开始{}",ids);
    setmealService.deleteBatch(ids);
return Result.success();
}
}
