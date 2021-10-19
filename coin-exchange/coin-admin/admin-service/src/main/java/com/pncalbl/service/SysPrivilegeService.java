package com.pncalbl.service;

import com.pncalbl.domain.SysPrivilege;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
public interface SysPrivilegeService extends IService<SysPrivilege> {

	/**
	 * 获取该菜单下的所有权限
	 *
	 * @param menuId 菜单 id
	 * @param roleId 当前查询的角色 id
	 * @return 权限
	 */
	List<SysPrivilege> getAllSysPrivilege(Long menuId, Long roleId);
}
