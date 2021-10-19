package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
public interface SysUserService extends IService<SysUser> {

	/**
	 * 条件分页查询
	 *
	 * @param page     分页参数
	 * @param mobile   员工的手机号码
	 * @param fullname 员工的全名
	 * @return 分页数据
	 */
	Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname);

	/**
	 * 新增员工
	 *
	 * @param sysUser 员工数据
	 * @return 是否新增成功
	 */
	boolean addUser(SysUser sysUser);
}
