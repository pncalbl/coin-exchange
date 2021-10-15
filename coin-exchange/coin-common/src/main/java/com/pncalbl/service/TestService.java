package com.pncalbl.service;

import com.pncalbl.model.WebLog;

/**
 * @author pncalbl
 * @date 2021/10/15 0:32
 * @e-mail pncalbl@qq.com
 * @description
 **/

public interface TestService {
	/**
	 * 通过用户名查询 weblog
	 * @param username 用户名
	 * @return weblog
	 */
	WebLog get(String username);
}
