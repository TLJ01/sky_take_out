package com.sky.service;

import com.sky.dto.SetmealDTO;

/**
 * 套餐管理
 */
public interface SetMealService {

    /**
     * 新增套餐
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);
}
