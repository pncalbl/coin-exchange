package com.pncalbl.controller;

import com.pncalbl.model.LoginResult;
import com.pncalbl.service.SysLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pncalbl
 * @date 2021/10/16 19:58
 * @e-mail pncalbl@qq.com
 * @description 登录的控制器
 **/

@RestController
@RequiredArgsConstructor
@Api(value = "登录的控制器")
public class SysLoginController {

	private final SysLoginService loginService;

	@PostMapping("/login")
	@ApiOperation(value = "后台管理人员的登录")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "username", value = "用户名"),
					@ApiImplicitParam(name = "password", value = "密码"),
			}
	)
	public LoginResult login(
			@RequestParam() String username,     // 用户名
			@RequestParam() String password) {   // 密码
		return loginService.login(username, password);
	}

}
