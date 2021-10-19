package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.SysPrivilege;
import com.pncalbl.mapper.SysPrivilegeMapper;
import com.pncalbl.service.SysPrivilegeService;
import org.springframework.util.CollectionUtils;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description 权限服务实现类
 **/

@Service
@RequiredArgsConstructor
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements SysPrivilegeService {

	private final SysPrivilegeMapper sysPrivilegeMapper;

	/**
	 * 获取该菜单下的所有权限
	 *
	 * @param menuId 菜单 id
	 * @param roleId 当前查询的角色 id
	 * @return 权限
	 */
	@Override
	public List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId) {
		// 1. 查询该菜单下的所有权限
		List<SysPrivilege> sysPrivileges = list(new LambdaQueryWrapper<SysPrivilege>().eq(SysPrivilege::getMenuId, menuId));
		if (CollectionUtils.isEmpty(sysPrivileges)) {
			return Collections.emptyList();
		}
		// 2. 当前传递的角色是否包含该权限信息
		for (SysPrivilege sysPrivilege : sysPrivileges) {
			Set<Long> currentRoleSysPrivilegeIds = sysPrivilegeMapper.getPrivilegesByRoleId(roleId);
			if (currentRoleSysPrivilegeIds.contains(sysPrivilege.getId())) {
				sysPrivilege.setOwn(1);  // 当前角色拥有该权限
			} else {
				sysPrivilege.setOwn(0);  // 当前角色不拥有该权限
			}
		}
		return sysPrivileges;
	}
}
