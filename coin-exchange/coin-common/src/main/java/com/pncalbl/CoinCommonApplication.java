package com.pncalbl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author pncalbl
 * @date 2021/10/14 22:12
 * @e-mail pncalbl@qq.com
 * @description
 **/

@SpringBootApplication
@EnableDiscoveryClient  // 开启服务注册与发现
public class CoinCommonApplication {
	public static void main(String[] args) {
		SpringApplication.run(CoinCommonApplication.class, args);
	}
}
