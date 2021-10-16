package com.pncalbl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pncalbl.domain.SysRole;

/**
*   @author pncalbl
*   @date 2021/10/15 20:37
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
public interface SysRoleMapper extends BaseMapper<SysRole> {

	/**
	 * 获取用户角色 code
	 * @param userId 用户 id
	 * @return 角色 code
	 */
	String getUserRoleCode(Long userId);
}