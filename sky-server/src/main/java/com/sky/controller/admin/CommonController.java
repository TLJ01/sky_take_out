package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by TanLiangJie
 * Time:2024/5/16 下午8:28
 */
@RestController
@Slf4j
@Api(tags = "通用接口")
@RequestMapping("/admin/common")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    public Result upload(MultipartFile file) {

        //获取原始文件后缀
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        //上传文件并获取路径-->第二个参数是上传到阿里云的名字

        //图片显示不出来,就把oss的读写权限改为公共读写,但可能会产生费用

        try {
            String path = aliOssUtil.upload(file.getBytes(), UUID.randomUUID()+ extension);
            return Result.success(path);
        } catch (IOException e) {
            log.info("文件上传失败:{}", e.getMessage());
        }
        return null;
    }

}
