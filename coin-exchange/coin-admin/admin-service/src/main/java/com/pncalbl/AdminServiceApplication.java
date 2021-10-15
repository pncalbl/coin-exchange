package com.pncalbl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author pncalbl
 * @date 2021/10/15 20:47
 * @e-mail pncalbl@qq.com
 * @description admin-service 启动类
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class AdminServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AdminServiceApplication.class, args);
	}
}
