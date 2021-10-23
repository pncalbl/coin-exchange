package com.pncalbl.service;

import com.pncalbl.domain.UserAuthInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
public interface UserAuthInfoService extends IService<UserAuthInfo> {


	/**
	 * 通过认证的 code, 查询用户认证详情
	 *
	 * @param authCode code
	 * @return 认证详情
	 */
	List<UserAuthInfo> getUserAuthInfoByCode(Long authCode);

	/**
	 * 未被认证的用户, 通过用户 id 查询用户认证详情
	 *
	 * @param id 用户 id
	 * @return 认证详情
	 */
	List<UserAuthInfo> getUserAuthInfoByUserId(Long id);
}
