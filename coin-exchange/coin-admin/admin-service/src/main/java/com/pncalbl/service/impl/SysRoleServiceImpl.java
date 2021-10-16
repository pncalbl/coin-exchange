package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.SysRole;
import com.pncalbl.mapper.SysRoleMapper;
import com.pncalbl.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

	private final SysRoleMapper sysRoleMapper;

	/**
	 * 判断一个用户是否为超级管理员
	 *
	 * @param userId 用户 id
	 * @return 是否为超级管理员
	 */
	@Override
	public boolean isSuperAdmin(Long userId) {
		// 当前用户的角色 code 为: ROLE_ADMIN 该用户是超级管理员
		// 用户的 id -> 用户的角色 -> 角色 code
		String roleCode = sysRoleMapper.getUserRoleCode(userId);
		return !StringUtils.isEmpty(roleCode) && roleCode.equals("ROLE_ADMIN");
	}
}
