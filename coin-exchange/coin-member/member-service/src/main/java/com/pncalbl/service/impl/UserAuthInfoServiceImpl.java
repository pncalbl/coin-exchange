package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.mapper.UserAuthInfoMapper;
import com.pncalbl.domain.UserAuthInfo;
import com.pncalbl.service.UserAuthInfoService;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
public class UserAuthInfoServiceImpl extends ServiceImpl<UserAuthInfoMapper, UserAuthInfo> implements UserAuthInfoService {

	/**
	 * 通过认证的 code, 查询用户认证详情
	 *
	 * @param authCode code
	 * @return 认证详情
	 */
	@Override
	public List<UserAuthInfo> getUserAuthInfoByCode(Long authCode) {
		return list(new LambdaQueryWrapper<UserAuthInfo>()
				.eq(UserAuthInfo::getAuthCode, authCode));
	}

	/**
	 * 未被认证的用户, 通过用户 id 查询用户认证详情
	 *
	 * @param id 用户 id
	 * @return 认证详情
	 */
	@Override
	public List<UserAuthInfo> getUserAuthInfoByUserId(Long id) {
		List<UserAuthInfo> list = list(new LambdaQueryWrapper<UserAuthInfo>()
				.eq(UserAuthInfo::getUserId, id));
		return list == null ? Collections.emptyList() : list;
	}
}
