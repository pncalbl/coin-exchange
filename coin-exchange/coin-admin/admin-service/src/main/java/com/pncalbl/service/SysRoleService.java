package com.pncalbl.service;

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
}
