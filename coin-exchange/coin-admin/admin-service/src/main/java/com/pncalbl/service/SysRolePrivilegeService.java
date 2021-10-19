package com.pncalbl.service;

import com.pncalbl.domain.SysMenu;
import com.pncalbl.domain.SysRolePrivilege;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pncalbl.model.RolePrivilegesParam;

import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description 角色权限服务
 **/
public interface SysRolePrivilegeService extends IService<SysRolePrivilege> {
	/**
	 * 查询角色的权限
	 *
	 * @param roleId 用户 id
	 * @return 角色的权限
	 */
	List<SysMenu> findSysMenuAndPrivileges(Long roleId);

	/**
	 * 授予角色某种权限
	 *
	 * @param rolePrivilegesParam 角色和权限数据
	 * @return 授予是否成功
	 */
	boolean grantPrivileges(RolePrivilegesParam rolePrivilegesParam);
}
