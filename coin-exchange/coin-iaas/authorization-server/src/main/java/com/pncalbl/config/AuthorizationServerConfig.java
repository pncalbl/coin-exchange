package com.pncalbl.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
// import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author pncalbl
 * @date 2021/10/10 22:20
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RequiredArgsConstructor
@EnableAuthorizationServer  // 开启授权服务器功能
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;


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
				.authorizedGrantTypes("password", "refresh_token")   // 授权类型
				.accessTokenValiditySeconds(7 * 24 * 3600)      // token 的有效期为 1周
				.refreshTokenValiditySeconds(30 * 24 * 3600)    // refreshToken 的有效期为 1月
				.and()  // 新增一个内部客户端
				.withClient("inside-app")
				.secret(passwordEncoder.encode("inside-secret"))
				.authorizedGrantTypes("client_credentials")
				.scopes("all")
				.accessTokenValiditySeconds(7 * 24 * 3600);
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
				.userDetailsService(userDetailsService)
				.tokenStore(jwtTokenStore())      // JWT 存储 token
				.tokenEnhancer(jwtAccessTokenConverter());

		super.configure(endpoints);
	}

	public TokenStore jwtTokenStore() {
		JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
		return jwtTokenStore;
	}

	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();

		// 加载我们的私钥
		ClassPathResource classPathResource = new ClassPathResource("coinexchange.jks");
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(classPathResource, "coinexchange".toCharArray());
		tokenConverter.setKeyPair(keyStoreKeyFactory.getKeyPair("coinexchange", "coinexchange".toCharArray()));
		return tokenConverter;
	}

}
