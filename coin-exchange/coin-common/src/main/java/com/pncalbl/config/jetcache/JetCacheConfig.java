package com.pncalbl.config.jetcache;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.context.annotation.Configuration;

/**
 * @author pncalbl
 * @date 2021/10/13 20:14
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Configuration
@EnableCreateCacheAnnotation
@EnableMethodCache(basePackages = "com.pncalbl.service.impl")
public class JetCacheConfig {

}
