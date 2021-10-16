package com.pncalbl.service;

import com.pncalbl.model.LoginResult;

/**
 * @author pncalbl
 * @date 2021/10/16 20:04
 * @e-mail pncalbl@qq.com
 * @description 登录的接口
 **/

public interface SysLoginService {

	/**
	 * 登录的实现
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录的结果
	 */
	LoginResult login(String username, String password);
}
