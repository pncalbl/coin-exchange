package com.pncalbl.controller;

import com.pncalbl.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pncalbl
 * @date 2021/10/21 20:18
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@Api(tags = "会员: 管理系统的测试")
public class TestController {

	@GetMapping("/test")
	@ApiOperation(value = "会员系统的测试", authorizations = {@Authorization("Authorization")})
	public R<String> test() {
		return R.ok("会员系统搭建成功");
	}
}
