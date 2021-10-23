package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.model.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.User;
import com.pncalbl.mapper.UserMapper;
import com.pncalbl.service.UserService;
import org.springframework.util.StringUtils;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description 用户管理服务实现类
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


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
	@Override
	public Page<User> findByPage(Page<User> page, String mobile, Long userId, String userName, String realName, Integer status, Integer reviewStatus) {
		return page(page, new LambdaQueryWrapper<User>()
				.like(!StringUtils.isEmpty(mobile), User::getMobile, mobile)
				.like(!StringUtils.isEmpty(userName), User::getUsername, userName)
				.like(!StringUtils.isEmpty(realName), User::getRealName, realName)
				.eq(userId != null, User::getId, userId)
				.eq(status != null, User::getStatus, status)
				.eq(reviewStatus != null, User::getReviewsStatus, reviewStatus)
		);
	}

	/**
	 * 条件分页查询 - 用户邀请列表
	 *
	 * @param page   分页参数
	 * @param userId 用户 id
	 * @return 分页数据
	 */
	@Override
	public Page<User> findDirectInvitesByPage(Page<User> page, Long userId) {
		return page(page, new LambdaQueryWrapper<User>()
				.eq(User::getDirectInviteid, userId));
	}
}
