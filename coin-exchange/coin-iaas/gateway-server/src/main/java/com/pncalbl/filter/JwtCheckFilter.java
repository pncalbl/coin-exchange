package com.pncalbl.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * @author pncalbl
 * @date 2021/10/11 21:09
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Component
@RequiredArgsConstructor
public class JwtCheckFilter implements GlobalFilter, Ordered {

	private final StringRedisTemplate redisTemplate;


	@Value("${no.require.urls:/admin/login, /user/gt/register}")
	private Set<String> noRequireTokenUrls;

	/**
	 * 过滤器拦截用户请求后所要进行的操作
	 *
	 * @param exchange 服务器网络交换
	 * @param chain    过滤链条
	 * @return 事物
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 1. 该接口是否需要 token 才能访问
		if (!isRequireToken(exchange)) {
			return chain.filter(exchange);  // 不需要 token , 直接放行
		}
		// 2. 取出用户的 token
		String token = getUserToken(exchange);
		// 3. 判断用户的 token 是否有效
		if (StringUtils.isEmpty(token)) {
			return buildNoAuthorizationResult(exchange);
		}

		Boolean hasKey = redisTemplate.hasKey(token);
		if (hasKey != null && hasKey) {
			return chain.filter(exchange);  // token 有效, 直接放行
		}
		return buildNoAuthorizationResult(exchange);
	}

	/**
	 * 给用户响应一个没有 token 的错误
	 *
	 * @param exchange 服务器网络交换
	 * @return 错误
	 */
	private Mono<Void> buildNoAuthorizationResult(ServerWebExchange exchange) {
		ServerHttpResponse response = exchange.getResponse();
		response.getHeaders().set("Content-Type", "application/json");
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("error", "NoAuthorization");
		jsonObject.put("errorMsg", "Token is Null or Error!");
		DataBuffer wrap = response.bufferFactory().wrap(
				jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
		return response.writeWith(Flux.just(wrap));
	}

	/**
	 * 从请求头获取用户的 token
	 *
	 * @param exchange 服务器网络交换
	 * @return token
	 */
	private String getUserToken(ServerWebExchange exchange) {
		String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		return token == null ? null : token.replace("bearer ", "");
	}

	/**
	 * 判断该接口是否需要 token
	 *
	 * @param exchange 服务器网络交换
	 * @return 是否需要 token
	 */
	private boolean isRequireToken(ServerWebExchange exchange) {
		String path = exchange.getRequest().getURI().getPath();
		if (noRequireTokenUrls.contains(path)) {
			return false;   // 不需要 token
		}
		return Boolean.TRUE;
	}

	/**
	 * 定义过滤器的拦截顺序
	 *
	 * @return 值
	 */
	@Override
	public int getOrder() {
		return 0;
	}
}
