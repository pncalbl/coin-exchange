package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pncalbl.model.R;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description 用户管理服务接口
 **/
public interface UserService extends IService<User> {


	/**
	 * 条件分页查询
	 *
	 * @param page         分页参数
	 * @param mobile       手机号
	 * @param userId       用户 id
	 * @param userName     用户名
	 * @param realName     真实姓名
	 * @param status       用户状态
	 * @param reviewStatus 会员的审核状态
	 * @return 分页数据
	 */
	Page<User> findByPage(Page<User> page, String mobile, Long userId, String userName, String realName, Integer status, Integer reviewStatus);


	/**
	 * 条件分页查询 - 用户邀请列表
	 *
	 * @param page   分页参数
	 * @param userId 用户 id
	 * @return 分页数据
	 */
	Page<User> findDirectInvitesByPage(Page<User> page, Long userId);
}
