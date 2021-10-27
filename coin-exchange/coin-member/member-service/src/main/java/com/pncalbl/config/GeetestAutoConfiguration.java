package com.pncalbl.config;

import com.pncalbl.geetest.GeetestLib;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pncalbl
 * @date 2021/10/23 20:30
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Configuration
@EnableConfigurationProperties(GeetestProperties.class)
public class GeetestAutoConfiguration {

	private GeetestProperties geetestProperties;

	public GeetestAutoConfiguration(GeetestProperties geetestProperties) {
		this.geetestProperties = geetestProperties;
	}

	@Bean
	public GeetestLib geetestLib() {
		return new GeetestLib(geetestProperties.getGeetestId(), geetestProperties.getGeetestKey());
	}
}
