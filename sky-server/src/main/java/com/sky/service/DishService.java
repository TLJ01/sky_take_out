package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;

public interface DishService {


    /**
     * 存入菜品,同时存入口味1
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

}
