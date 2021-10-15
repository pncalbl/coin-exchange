package com.pncalbl.config.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author pncalbl
 * @date 2021/10/13 20:33
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.sessionManagement().disable()
				.authorizeRequests()
				.antMatchers(
						"/v2/api-docs",
						"/swagger-resources/configuration/ui",//用来获取支持的动作
						"/swagger-resources",//用来获取api-docs的URI
						"/swagger-resources/configuration/security",//安全选项
						"/webjars/**",
						"/swagger-ui.html").permitAll()  // 放行
				.antMatchers("/**").authenticated() // 需要登录
				.and().headers().cacheControl();
	}

	/**
	 * 设置公钥
	 *
	 * @param resources 安全配置
	 */
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.tokenStore(jwtTokenStore());
	}

	private TokenStore jwtTokenStore() {
		JwtTokenStore jwtTokenStore = new JwtTokenStore(accessTokenConverter());
		return jwtTokenStore;
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		// resource 是用来验证 token         - 公钥
		// 而 authorization 是用来产生 token - 私钥
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		String publicKey = null;
		try {

			ClassPathResource classPathResource = new ClassPathResource("coinexchange.pub");
			byte[] bytes = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
			publicKey = new String(bytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			log.info("读取公钥失败");
		}
		tokenConverter.setVerifierKey(publicKey);
		return tokenConverter;
	}
}
