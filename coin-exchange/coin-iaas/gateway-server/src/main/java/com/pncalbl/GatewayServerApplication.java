package com.pncalbl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author pncalbl
 * @date 2021/10/8 20:01
 * @e-mail pncalbl@qq.com
 * @description
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}
}
