package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.UserAuthAuditRecord;
import com.pncalbl.model.R;
import com.pncalbl.service.UserAuthAuditRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.User;
import com.pncalbl.mapper.UserMapper;
import com.pncalbl.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description 用户管理服务实现类
 **/
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Resource
	private UserAuthAuditRecordService userAuthAuditRecordService;

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

	/**
	 * 修改用户的审核状态
	 *
	 * @param id         用户的Id
	 * @param authStatus 审核状态
	 * @param authCode   一组认证图片的唯一标识
	 * @param remark     审核拒绝原因
	 */
	@Override
	@Transactional
	public void updateUserAuthStatus(Long id, Integer authStatus, Long authCode, String remark) {
		log.info("开始修改用户的状态, 当前用户{}, 用户的审核状态{}, 图片的唯一 code{}.", id, authStatus, authCode);
		User user = getById(id);
		if (user != null) {
			// user.setAuthStatus(authStatus.byteValue());  // 认证状态
			user.setReviewsStatus(authStatus);  // 审核状态
			updateById(user);   // 修改用户的状态
		}

		String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

		UserAuthAuditRecord userAuthAuditRecord = new UserAuthAuditRecord();
		userAuthAuditRecord.setUserId(id);
		userAuthAuditRecord.setStatus(authStatus.byteValue());
		userAuthAuditRecord.setAuthCode(authCode);
		userAuthAuditRecord.setAuditUserId(Long.valueOf(userId));   // 审核人 id
		userAuthAuditRecord.setAuditUserName("---------------->"); // 审核人姓名    -> 远程调用 admin-service
		userAuthAuditRecord.setRemark(remark);
		userAuthAuditRecordService.save(userAuthAuditRecord);
	}
}
