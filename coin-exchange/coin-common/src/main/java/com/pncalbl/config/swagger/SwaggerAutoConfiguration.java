package com.pncalbl.config.swagger;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/13 20:52
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerAutoConfiguration {

	private final SwaggerProperties swaggerProperties;

	public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties) {
		this.swaggerProperties = swaggerProperties;
	}


	@Bean
	public Docket docket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
				.paths(PathSelectors.any())
				.build();
		// 安全的配置
		docket.securitySchemes(securitySchemes())       // 安全的规则
				.securityContexts(securityContexts());  // 安全配置的上下文

		return docket;
	}

	private List<SecurityContext> securityContexts() {
		return Collections.singletonList(new SecurityContext(
				Collections.singletonList(new SecurityReference("Authorization", new AuthorizationScope[]{new AuthorizationScope("global", "accessResource")})),
				PathSelectors.any()
		));
	}

	/**
	 * 安全的规则配置
	 *
	 * @return 配置列表
	 */
	private List<SecurityScheme> securitySchemes() {
		return Collections.singletonList(new ApiKey("Authorization", "Authorization", "Authorization"));
	}

	/**
	 * api 信息的简介
	 *
	 * @return api 信息
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().contact(
				new Contact(swaggerProperties.getName(), swaggerProperties.getUrl(), swaggerProperties.getEmail()))
				.title(swaggerProperties.getTitle())
				.description(swaggerProperties.getDescription())
				.version(swaggerProperties.getVersion())
				.termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
				.build();
	}
}
