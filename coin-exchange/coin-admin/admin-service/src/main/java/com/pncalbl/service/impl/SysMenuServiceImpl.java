package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.SysMenu;
import com.pncalbl.mapper.SysMenuMapper;
import com.pncalbl.service.SysMenuService;
import com.pncalbl.service.SysRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

	private final SysRoleService sysRoleService;

	private final SysMenuMapper sysMenuMapper;

	/**
	 * 通过用户的 id, 查询用户的菜单数据
	 *
	 * @param userId 用户 id
	 * @return 用户的菜单数据
	 */
	@Override
	public List<SysMenu> getMenusByUserId(Long userId) {
		// 1. 如果该用户是一个超级管理员 -> 拥有所有的菜单
		if (sysRoleService.isSuperAdmin(userId)) {
			return list();  // 查询所有
		}
		// 2. 如果该用户不是超级管理员 -> 查询角色 -> 查询菜单数据
		return sysMenuMapper.selectMenusByUserId(userId);
	}
}
