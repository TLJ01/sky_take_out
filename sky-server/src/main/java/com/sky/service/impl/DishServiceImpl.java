package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by TanLiangJie
 * Time:2024/5/16 下午9:31
 */
@Service
@Transactional//操作两张表,保持一致
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetMealDishMapper setMealDishMapper;

    @Override
    public void saveWithFlavor(DishDTO dishDTO) {

        //存入菜品表
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.save(dish);

        Long dishId = dish.getId();

        //获取flavor
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null && flavors.size()>0) {

            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dishId);
            }

            dishFlavorMapper.save(flavors);
        }

    }


    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        //查询列表
        Page<DishVO> page = dishMapper.list(dishPageQueryDTO);

        return new PageResult(page.getTotal(),page.getResult());
    }


    /**
     * 批量删除菜品
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {

        //起售中的菜品不能删除
        for (Long id : ids) {
            //先查询菜品
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus()==StatusConstant.ENABLE)throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }

        //关联了套餐的菜品也不能删除
        //查询
        List<Long> setMealIds = setMealDishMapper.getSetMealIdsByDishIds(ids);

        if (setMealIds!=null&&setMealIds.size()>0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //删除
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByIds(ids);

    }


    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Override
    public DishVO getById(Long id) {
        Dish dish = dishMapper.getById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        //获取口味
        List<DishFlavor> flavors = dishFlavorMapper.getById(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }


    /**
     * 修改菜品状态
     * @param status
     * @param id
     */
    @Override
    public void status(Integer status, Long id) {
        //封装
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setId(id);

        //调用之前写的更新操作
        dishMapper.update(dish);
    }

    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> getByCategoryId(Integer categoryId) {
        return dishMapper.getByCategoryId(categoryId);
    }

    /**
     * 修改菜品
     * @param dishDTO
     */
    @Override
    public void update(DishDTO dishDTO) {

        //修改菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        //修改口味表
        //先删除口味,再添加
        dishFlavorMapper.deleteById(dish.getId());
        //获取口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors!=null && flavors.size()>0) {

            for (DishFlavor flavor : flavors) {
                flavor.setDishId(dish.getId());
            }

            dishFlavorMapper.save(flavors);
        }

    }



}
