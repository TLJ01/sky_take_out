package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    /**
     * 新增
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 获取列表--分页
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 状态
     * @param status
     * @param id
     */
    void status(Integer status, Long id);

    /**
     * 更新
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 根据类型查询
     *
     * @param type
     * @return
     */
    List<Category> getByType(Integer type);
}
