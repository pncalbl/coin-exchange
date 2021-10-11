package com.pncalbl.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author pncalbl
 * @date 2021/10/11 19:57
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
public class UserInfoController {

	/**
	 * 当前登录的用户对象
	 *
	 * @param principal 主要对象
	 * @return 用户对象
	 */
	@GetMapping("/user/info")
	public Principal userInfo(Principal principal) {
		// 使用 ThreadLocal 来实现的
		// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return principal;
	}
}
