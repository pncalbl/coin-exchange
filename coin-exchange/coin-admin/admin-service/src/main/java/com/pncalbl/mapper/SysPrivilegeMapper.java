package com.pncalbl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pncalbl.domain.SysPrivilege;

import java.util.Set;

/**
*   @author pncalbl
*   @date 2021/10/15 20:37
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {

	/**
	 * 使用角色的 id 查询权限的 id
	 * @param roleId 角色 id
	 * @return 权限 id
	 */
	Set<Long> getPrivilegesByRoleId(Long roleId);
}