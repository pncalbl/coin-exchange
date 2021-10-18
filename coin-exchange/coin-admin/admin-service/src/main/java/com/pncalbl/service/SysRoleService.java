package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
public interface SysRoleService extends IService<SysRole> {

	/**
	 * 判断一个用户是否为超级管理员
	 *
	 * @param userId 用户 id
	 * @return 是否为超级管理员
	 */
	boolean isSuperAdmin(Long userId);

	/**
	 * 根据使用角色的名称分页角色查询
	 *
	 * @param page 分页对象
	 * @param name 角色名称
	 * @return 分页角色对象
	 */
	Page<SysRole> findByPage(Page<SysRole> page, String name);
}
