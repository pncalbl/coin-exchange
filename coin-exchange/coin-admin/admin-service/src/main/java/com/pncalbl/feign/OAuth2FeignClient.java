package com.pncalbl.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author pncalbl
 * @date 2021/10/16 20:18
 * @e-mail pncalbl@qq.com
 * @description
 **/

@FeignClient(value = "authorization-server")
public interface OAuth2FeignClient {

	@PostMapping("/oauth/token")
	ResponseEntity<JwtToken> getToken(
			@RequestParam("grant_type") String grantType,   // 授权类型
			@RequestParam("username") String username,      // 用户名
			@RequestParam("password") String password,      // 用户密码
			@RequestParam("login_type") String loginType,   // 登录类型
			@RequestHeader("Authorization") String basicToken);  // Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ= 由第三方客户端加密而成
}
