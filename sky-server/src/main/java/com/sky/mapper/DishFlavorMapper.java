package com.sky.mapper;


import com.sky.annotation.AutoFill;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 存入口味表
     * @param flavors
     */
    void save(List<DishFlavor> flavors);


    /**
     * 根据菜品id删除口味
     * @param id
     */
    @Delete("delete from dish_flavor where dish_id=#{id}")
    void deleteById(Long id);

    /**
     * 根据菜品集合删除口味
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 修改口味表
     * @param flavors
     */
    void update(List<DishFlavor> flavors);

    /**
     * 获取口味
     * @param id
     */
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> getById(Long id);
}
