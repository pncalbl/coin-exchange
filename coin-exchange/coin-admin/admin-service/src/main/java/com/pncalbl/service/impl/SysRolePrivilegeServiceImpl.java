package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.SysMenu;
import com.pncalbl.domain.SysPrivilege;
import com.pncalbl.domain.SysRolePrivilege;
import com.pncalbl.mapper.SysRolePrivilegeMapper;
import com.pncalbl.model.RolePrivilegesParam;
import com.pncalbl.service.SysMenuService;
import com.pncalbl.service.SysPrivilegeService;
import com.pncalbl.service.SysRolePrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description 角色权限服务实现类
 **/
@Service
@RequiredArgsConstructor
public class SysRolePrivilegeServiceImpl extends ServiceImpl<SysRolePrivilegeMapper, SysRolePrivilege> implements SysRolePrivilegeService {

	private final SysMenuService sysMenuService;
	private final SysPrivilegeService sysPrivilegeService;
	@Resource
	private SysRolePrivilegeService sysRolePrivilegeService;    // 使用构造函数注入有循环依赖的问题

	/**
	 * 查询角色的权限
	 *
	 * @param roleId 用户 id
	 * @return 角色的权限
	 */
	@Override
	public List<SysMenu> findSysMenuAndPrivileges(Long roleId) {
		List<SysMenu> list = sysMenuService.list();
		// 我们在页面显示的是二级菜单，以及二级菜单包含的菜单
		if (CollectionUtils.isEmpty(list)) {
			return Collections.emptyList();
		}
		List<SysMenu> rootMenus = list.stream()
				.filter(sysMenu -> sysMenu.getParentId() == null)
				.collect(Collectors.toList());
		if (CollectionUtils.isEmpty(rootMenus)) {
			return Collections.emptyList();
		}

		// 查询所有的二级菜单
		List<SysMenu> subMenus = new ArrayList<>();
		for (SysMenu rootMenu : rootMenus) {
			subMenus.addAll(getSubMenus(rootMenu.getId(), roleId, list));
		}
		return subMenus;
	}

	/**
	 * 授予角色某种权限
	 *
	 * @param rolePrivilegesParam 角色和权限数据
	 * @return 授予是否成功
	 */
	@Transactional
	@Override
	public boolean grantPrivileges(RolePrivilegesParam rolePrivilegesParam) {
		Long roleId = rolePrivilegesParam.getRoleId();
		// 1. 删除该角色之前的权限
		sysRolePrivilegeService.
				remove(new LambdaQueryWrapper<SysRolePrivilege>()
						.eq(SysRolePrivilege::getRoleId, roleId));

		List<Long> privilegeIds = rolePrivilegesParam.getPrivilegeIds();
		if (!CollectionUtils.isEmpty(privilegeIds)) {
			// 2. 新增该角色的权限
			List<SysRolePrivilege> sysRolePrivileges = new ArrayList<>();
			for (Long privilegeId : privilegeIds) {
				SysRolePrivilege sysRolePrivilege = new SysRolePrivilege();
				sysRolePrivilege.setRoleId(rolePrivilegesParam.getRoleId());
				sysRolePrivilege.setPrivilegeId(privilegeId);
				sysRolePrivileges.add(sysRolePrivilege);
			}
			return sysRolePrivilegeService.saveBatch(sysRolePrivileges);
		}
		return true;
	}

	/**
	 * 查询菜单的子菜单
	 *
	 * @param parentId 父菜单 id
	 * @param roleId   当前查询的角色 id
	 * @param sources  数据源
	 * @return 子菜单
	 */
	private List<SysMenu> getSubMenus(Long parentId, Long roleId, List<SysMenu> sources) {
		List<SysMenu> subMenus = new ArrayList<>();
		for (SysMenu source : sources) {
			if (source.getParentId() == parentId) {
				subMenus.add(source);
				source.setSubMenus(getSubMenus(source.getId(), roleId, sources));
				List<SysPrivilege> sysPrivileges = sysPrivilegeService.getAllSysPrivilege(source.getId(), roleId);
				source.setPrivileges(sysPrivileges); // 该子菜单可能包含的权限
			}
		}
		return subMenus;
	}
}
