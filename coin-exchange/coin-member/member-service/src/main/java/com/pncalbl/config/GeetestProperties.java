package com.pncalbl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author pncalbl
 * @date 2021/10/23 20:27
 * @e-mail pncalbl@qq.com
 * @description 极验配置类
 **/


@Data
@ConfigurationProperties(prefix = "geetest")
public class GeetestProperties {

	/**
	 * 极验 id
	 */
	private String geetestId;

	/**
	 * 极验  key
	 */
	private String geetestKey;
}
