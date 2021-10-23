package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.UserBank;
import com.pncalbl.model.R;
import com.pncalbl.service.UserBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author pncalbl
 * @date 2021/10/22 20:33
 * @e-mail pncalbl@qq.com
 * @description 用户银行卡管理
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "会员: 用户银行卡管理")
@RequestMapping("/userBanks")
public class UserBankController {

	private final UserBankService userBankService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "usrId", value = "用户 id"),
			}
	)
	@PreAuthorize("hasAuthority('user_bank_query')")
	public R<Page<UserBank>> findByPage(@ApiIgnore Page<UserBank> page, Long usrId) {
		page.addOrder(OrderItem.desc("last_update_time"));
		return R.ok(userBankService.findByPage(page, usrId));
	}


	@ApiOperation(value = "修改银行卡状态")
	@PostMapping("/status")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "银行卡的 id"),
					@ApiImplicitParam(name = "status", value = "银行卡状态"),
			}
	)
	public R updateStatus(Long id, Byte status) {
		UserBank userBank = new UserBank();
		userBank.setId(id);
		userBank.setStatus(status);
		boolean update = userBankService.updateById(userBank);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}

	@ApiOperation(value = "修改银行卡")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "userBank", value = "userBank 的 json 数据"),
			}
	)
	public R update(@RequestBody @Validated UserBank userBank) {
		boolean update = userBankService.updateById(userBank);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}
}
