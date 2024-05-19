package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by TanLiangJie
 * Time:2024/5/19 下午4:45
 */
@RestController
@Api(tags = "店铺controller")
@Slf4j
@RequestMapping("/admin/shop")
public class ShopController
{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取店铺状态
     * @return
     */

    @GetMapping("/status")
    @ApiOperation("获取店铺状态")
    public Result<Integer>getStatus(){
        Integer status = Integer.valueOf(stringRedisTemplate.opsForValue().get("SHOP_STATUS"));
        log.info("当前店铺状态:{}",status==1?"营业":"打烊");
        return Result.success(status);
    }


    /**
     * 设置营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("status:{}",status);
        stringRedisTemplate.opsForValue().set("SHOP_STATUS", String.valueOf(status));
        return Result.success();
    }

}
