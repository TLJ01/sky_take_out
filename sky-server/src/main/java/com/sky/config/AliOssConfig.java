package com.sky.config;

import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by TanLiangJie
 * Time:2024/5/16 下午8:42
 */
@Configuration
public class AliOssConfig {


    /**
     * 生成AliOssUtil对象
     * @param aliOssProperties
     * @return
     */
    @Bean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){

        /**
         * 初始AliOssUtil对象并返回,,,aliOssProperties这是配置属性
         */
        return new AliOssUtil(aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }

}
