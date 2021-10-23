package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.User;
import com.pncalbl.domain.UserAuthAuditRecord;
import com.pncalbl.domain.UserAuthInfo;
import com.pncalbl.model.R;
import com.pncalbl.service.UserAuthAuditRecordService;
import com.pncalbl.service.UserAuthInfoService;
import com.pncalbl.service.UserService;
import com.pncalbl.vo.UseAuthInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/21 20:46
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "会员: 用户管理")
@RequestMapping("/users")
public class UserController {

	private final UserService userService;
	private final UserAuthAuditRecordService userAuthAuditRecordService;
	private final UserAuthInfoService userAuthInfoService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "mobile", value = "手机号"),
					@ApiImplicitParam(name = "userId", value = "用户 id"),
					@ApiImplicitParam(name = "userName", value = "用户名"),
					@ApiImplicitParam(name = "realName", value = "真实姓名"),
					@ApiImplicitParam(name = "status", value = "用户状态"),
			}
	)
	@PreAuthorize("hasAuthority('user_query')")
	public R<Page<User>> findByPage(@ApiIgnore Page<User> page, String mobile,
	                                Long userId, String userName, String realName,
	                                Integer status) {
		page.addOrder(OrderItem.desc("last_update_time"));
		return R.ok(userService.findByPage(page, mobile, userId, userName, realName, status, null));
	}

	@ApiOperation(value = "修改用户状态")
	@PostMapping("/status")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "用户 id"),
					@ApiImplicitParam(name = "status", value = "用户状态"),
			}
	)
	@PreAuthorize("hasAuthority('user_update')")
	public R updateStatus(Long id, Byte status) {
		User user = new User();
		user.setId(id);
		user.setStatus(status);
		boolean update = userService.updateById(user);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}

	@ApiOperation(value = "修改用户")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "user", value = "user 的 json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('user_update')")
	public R update(@RequestBody @Validated User user) {
		boolean update = userService.updateById(user);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}

	@ApiOperation(value = "查询用户的详细信息")
	@GetMapping("/info")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "用户 id"),
			}
	)
	public R<User> userInfo(Long id) {
		User user = userService.getById(id);
		return R.ok(user);
	}

	@ApiOperation(value = "查询用户的邀请列表")
	@GetMapping("/directInvites")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "userId", value = "用户 userId"),
			}
	)
	@PreAuthorize("hasAuthority('user_query')")
	public R<Page<User>> getDirectInvites(@ApiIgnore Page<User> page, Long userId) {
		Page<User> userPage = userService.findDirectInvitesByPage(page, userId);
		return R.ok(userPage);
	}

	@ApiOperation(value = "查询用户的审核列表")
	@GetMapping("/auths")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "mobile", value = "手机号"),
					@ApiImplicitParam(name = "userId", value = "用户 id"),
					@ApiImplicitParam(name = "userName", value = "用户名"),
					@ApiImplicitParam(name = "realName", value = "真实姓名"),
					@ApiImplicitParam(name = "reviewsStatus", value = "用户审核状态"),
			}
	)
	@PreAuthorize("hasAuthority('user_query')")
	public R<Page<User>> findUserAuths(@ApiIgnore Page<User> page, String mobile,
	                                   Long userId, String userName, String realName,
	                                   Integer reviewsStatus) {
		Page<User> userPage = userService.findByPage(page, mobile, userId, userName, realName, null, reviewsStatus);
		return R.ok(userPage);
	}

	@ApiOperation(value = "查询用户的认证详情")
	@GetMapping("/auth/info")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户的Id")
	})
	@PreAuthorize("hasAuthority('user_auth_query')")
	public R<UseAuthInfoVo> getUseAuthInfo(Long id) {

		User user = userService.getById(id);
		List<UserAuthAuditRecord> userAuthAuditRecords = null;
		List<UserAuthInfo> userAuthInfoList = null;

		if (user != null) {
			// 用户的审核记录
			Integer reviewsStatus = user.getReviewsStatus();
			if (reviewsStatus != null || reviewsStatus == 0) {
				// 待审核
				userAuthAuditRecords = Collections.emptyList();
				userAuthInfoList = userAuthInfoService.getUserAuthInfoByUserId(id);
			} else {
				userAuthAuditRecords = userAuthAuditRecordService.getUserAuthInfoByUserId(id);

				// 查询用户的认证详情列表 -> 用户的身份信息
				// 之前我们查询时, 是按照的日志排序的,
				// 第 0 个值, 就是最近被认证的值
				UserAuthAuditRecord userAuthAuditRecord = userAuthAuditRecords.get(0);
				// 认证的唯一标识
				Long authCode = userAuthAuditRecord.getAuthCode();

				userAuthInfoList = userAuthInfoService.getUserAuthInfoByCode(authCode);
			}
		}
		return R.ok(new UseAuthInfoVo(user, userAuthInfoList, userAuthAuditRecords));
	}

}
