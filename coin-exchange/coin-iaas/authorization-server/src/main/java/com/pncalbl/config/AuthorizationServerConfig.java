package com.pncalbl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * @author pncalbl
 * @date 2021/10/10 22:20
 * @e-mail pncalbl@qq.com
 * @description
 **/

@EnableAuthorizationServer  // 开启授权服务器功能
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	/**
	 * 配置第三方客户端
	 *
	 * @param clients 客户端
	 * @throws Exception 异常
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
				.withClient("coin-api") // 第三方客户端名称
				.secret(passwordEncoder.encode("coin-secret")) // 第三方客户端的密钥
				.scopes("all")   // 第三方客户端的授权范围
				.accessTokenValiditySeconds(3600)      // token 的有效期
				.refreshTokenValiditySeconds(7 * 3600);    // refreshToken 的有效期
		super.configure(clients);
	}

	/**
	 * 配置验证管理器, userDetailsService
	 *
	 * @param endpoints 端点
	 * @throws Exception 异常
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(userDetailsService);

		super.configure(endpoints);
	}
}
