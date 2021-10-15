package com.pncalbl.controller;

import com.pncalbl.model.R;
import com.pncalbl.model.WebLog;
import com.pncalbl.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author pncalbl
 * @date 2021/10/14 22:27
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@Api(tags = "coin-common 测试接口")
public class TestController {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private TestService testService;

	@GetMapping("/common/test")
	@ApiOperation(value = "测试方法", authorizations = {@Authorization("Authorization")})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "param0", value = "参数1", dataType = "String", paramType = "query", example = "paramValue"),
			@ApiImplicitParam(name = "param1", value = "参数2", dataType = "String", paramType = "query", example = "paramValue"),
	})
	public R<String> testMethod(String param0, String param1) {
		return R.ok("Ok");
	}

	@GetMapping("/date/test")
	@ApiOperation(value = "日期格式化测试", authorizations = {@Authorization("Authorization")})
	public R<Date> testDate() {
		return R.ok(new Date());
	}

	@GetMapping("/redis/test")
	@ApiOperation(value = "redis 测试", authorizations = {@Authorization("Authorization")})
	public R<String> testRedis() {
		WebLog webLog = new WebLog();
		webLog.setResult("ok");
		webLog.setMethod("com.pncalbl.domain.WebLog.testRedis");
		webLog.setUsername("1110");
		redisTemplate.opsForValue().set("com.pncalbl.domain.WebLog", webLog);
		return R.ok("Ok");
	}

	@GetMapping("/jetcache/test")
	@ApiOperation(value = "jetCache 测试", authorizations = {@Authorization("Authorization")})
	public R<String> testJetCache(String username) {
		WebLog webLog = testService.get(username);
		System.out.println(webLog);
		return R.ok("Ok");
	}

}
