package com.pncalbl.config;

import com.pncalbl.filter.CorsResponseHeaderFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.DefaultCorsProcessor;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @author pncalbl
 * @date 2021/10/23 21:29
 * @e-mail pncalbl@qq.com
 * @description 解决跨域问题
 **/


@Configuration
public class CorsConfig {
	@Bean
	public CorsResponseHeaderFilter corsResponseHeaderFilter() {
		return new CorsResponseHeaderFilter();
	}

	@Bean
	public CorsWebFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
		source.registerCorsConfiguration("/**", buildCorsConfiguration());

		CorsWebFilter corsWebFilter = new CorsWebFilter(source, new DefaultCorsProcessor() {
			@Override
			protected boolean handleInternal(ServerWebExchange exchange, CorsConfiguration config,
			                                 boolean preFlightRequest) {
				return super.handleInternal(exchange, config, preFlightRequest);
			}
		});

		return corsWebFilter;
	}

	private CorsConfiguration buildCorsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.setMaxAge(7200L);
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}
}

