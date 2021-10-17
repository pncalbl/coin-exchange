package com.pncalbl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.pncalbl.domain.SysMenu;
import com.pncalbl.feign.JwtToken;
import com.pncalbl.feign.OAuth2FeignClient;
import com.pncalbl.model.LoginResult;
import com.pncalbl.service.SysLoginService;
import com.pncalbl.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author pncalbl
 * @date 2021/10/16 20:08
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class SysLoginServiceImpl implements SysLoginService {

	private final OAuth2FeignClient oAuth2FeignClient;
	private final SysMenuService sysMenuService;
	private final StringRedisTemplate redisTemplate;

	@Value("${basic.token:Basic Y29pbi1hcGk6Y29pbi1zZWNyZXQ=}")
	private String basicToken;

	/**
	 * 登录的实现
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录的结果
	 */
	@Override
	public LoginResult login(String username, String password) {
		log.info("用户{}开始登录", username);
		// 1. 获取 token, 需要远程调用 authorization-server 该服务
		ResponseEntity<JwtToken> tokenResponseEntity = oAuth2FeignClient
				.getToken("password", username, password, "admin_type", basicToken);
		if (tokenResponseEntity.getStatusCode() != HttpStatus.OK) {
			throw new ApiException(ApiErrorCode.FAILED);
		}
		JwtToken jwtToken = tokenResponseEntity.getBody();
		log.info("远程调用授权服务器成功, 获取的 token 为{}", JSON.toJSONString(jwtToken, true));
		assert jwtToken != null;
		String token = jwtToken.getAccessToken();

		// 2. 查询我们的菜单数据
		Jwt jwt = JwtHelper.decode(token);  // 解码 token
		String jwtJsonStr = jwt.getClaims();    // 获取 json 字符串
		JSONObject jwtJson = JSON.parseObject(jwtJsonStr); // 转换为 JSON 对象
		Long userId = Long.valueOf(jwtJson.getString("user_name"));  // 获取用户 id
		List<SysMenu> menus = sysMenuService.getMenusByUserId(userId);   // 获取用户菜单数据

		// 3. 权限数据怎么获取 -- 不需要查询, jwt 已经包含了
		JSONArray authoritiesJsonArray = jwtJson.getJSONArray("authorities");
		List<SimpleGrantedAuthority> authorities = authoritiesJsonArray.stream()    // 组装我们的权限数据
				.map(authorityJson -> new SimpleGrantedAuthority(authorityJson.toString()))
				.collect(Collectors.toList());

		// 1 将该token 存储在redis 里面，配置我们的网关做jwt验证的操作
		redisTemplate.opsForValue().set(token, "", jwtToken.getExpiresIn(), TimeUnit.SECONDS);
		//2 我们返回给前端的Token 数据，少一个 bearer：
		return new LoginResult(jwtToken.getTokenType() + " " + token, menus, authorities);
	}
}
