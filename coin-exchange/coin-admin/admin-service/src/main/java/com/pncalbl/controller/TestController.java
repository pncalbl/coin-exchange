package com.pncalbl.controller;

import com.pncalbl.domain.SysUser;
import com.pncalbl.model.R;
import com.pncalbl.service.SysUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author pncalbl
 * @date 2021/10/15 21:31
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台管理系统的测试接口")
public class TestController {

	private final SysUserService sysUserService;

	@ApiOperation(value = "查询用户详情", authorizations = {@Authorization("Authorization")})
	@GetMapping("/user/{id}")
	@ApiImplicitParams(
			@ApiImplicitParam(name = "id", value = "用户的 id")
	)
	public R<SysUser> getSysUserInfo(@PathVariable("id") Long id) {
		SysUser sysUser = sysUserService.getById(id);
		return R.ok(sysUser);
	}
}
