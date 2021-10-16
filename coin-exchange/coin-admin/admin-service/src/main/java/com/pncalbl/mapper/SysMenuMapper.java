package com.pncalbl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pncalbl.domain.SysMenu;

import java.util.List;

/**
*   @author pncalbl
*   @date 2021/10/15 20:37
*   @e-mail pncalbl@qq.com
*   @description
*   ${TAGES}
**/
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	/**
	 * 通过用户的 id, 查询用户的菜单数据
	 * @param userId 用户 id
	 * @return 用户的菜单数据
	 */
	List<SysMenu> selectMenusByUserId(Long userId);
}