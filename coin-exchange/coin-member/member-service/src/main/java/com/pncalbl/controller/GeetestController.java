package com.pncalbl.controller;

import com.pncalbl.geetest.DigestmodEnum;
import com.pncalbl.geetest.GeetestLib;
import com.pncalbl.geetest.GeetestLibResult;
import com.pncalbl.model.R;
import com.pncalbl.util.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author pncalbl
 * @date 2021/10/23 20:46
 * @e-mail pncalbl@qq.com
 * @description 极验验证控制类
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "会员: 极验验证管理")
@RequestMapping("/gt")
public class GeetestController {

	private final GeetestLib gtLib;
	private final RedisTemplate<String, Object> redisTemplate;

	@ApiOperation(value = "获取极验的第一次数据包")
	@GetMapping("/register")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "uuid", value = "用户验证的一个凭证"),
			}
	)
	public R<String> register(String uuid) {

		DigestmodEnum digestmodEnum = DigestmodEnum.MD5;
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("digestmod", digestmodEnum.getName());
		paramMap.put("user_id", uuid);
		paramMap.put("client_type", "web");
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
				RequestContextHolder.getRequestAttributes();
		assert requestAttributes != null;
		paramMap.put("ip_address", IpUtil.getIpAddr(requestAttributes.getRequest()));

		GeetestLibResult result = gtLib.register(digestmodEnum, paramMap);  // 和极验服务器交互
		redisTemplate.opsForValue().set(GeetestLib.GEETEST_SERVER_STATUS_SESSION_KEY, result.getStatus(), 180, TimeUnit.SECONDS);
		redisTemplate.opsForValue().set(GeetestLib.GEETEST_SERVER_USER_KEY + ":" + uuid, uuid, 180, TimeUnit.SECONDS);
		return R.ok(result.getData());
	}
}
