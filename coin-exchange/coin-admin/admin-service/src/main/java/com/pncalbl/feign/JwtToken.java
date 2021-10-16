package com.pncalbl.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author pncalbl
 * @date 2021/10/16 20:19
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Data
public class JwtToken {
	/**
	 * 授权的 token
	 */
	@JsonProperty("access_token")
	private String accessToken;

	/**
	 * token 的类型
	 */
	@JsonProperty("token_type")
	private String tokenType;

	/**
	 * refresh_token
	 */
	@JsonProperty("refresh_token")
	private String refreshToken;

	/**
	 * token 有效期
	 */
	@JsonProperty("expires_in")
	private long expiresIn;

	/**
	 * token 作用域
	 */
	private String scope;

	/**
	 * 颁发的凭证
	 */
	private String jti;
}
