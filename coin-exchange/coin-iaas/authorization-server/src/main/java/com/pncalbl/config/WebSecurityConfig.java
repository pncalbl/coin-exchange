package com.pncalbl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Collections;

/**
 * @author pncalbl
 * @date 2021/10/10 22:34
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();  // 禁用跨域
		http.authorizeRequests().anyRequest().authenticated(); // 允许所有请求
	}

	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	// @Bean
	// public UserDetailsService userDetailsService() {
	// 	InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
	// 	User user = new User("admin", "123456",
	// 			Collections.singletonList(new SimpleGrantedAuthority("Role_Admin")));
	// 	inMemoryUserDetailsManager.createUser(user);
	// 	return inMemoryUserDetailsManager;
	// }

	/**
	 * 密码加密
	 *
	 * @return 密码编码器
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 测试加密
	// public static void main(String[] args) {
	// 	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	// 	String encode = passwordEncoder.encode("123456");
	// 	System.out.println(encode);
	// }
}
