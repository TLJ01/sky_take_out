package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.SetMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by TanLiangJie
 * Time:2024/5/18 下午11:01
 */

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/admin/setmeal")
@Api(tags = "套餐管理")
public class SetMealController {

    @Autowired
    private SetMealService setMealService;


    /**
     * 新增套餐
     *
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增套餐")
    public Result save(@RequestBody SetmealDTO setmealDTO) {
        setMealService.save(setmealDTO);
        return Result.success();
    }

}