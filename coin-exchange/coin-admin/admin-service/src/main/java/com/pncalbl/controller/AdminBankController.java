package com.pncalbl.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.AdminBank;
import com.pncalbl.model.R;
import com.pncalbl.service.AdminBankService;
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
 * @date 2021/10/21 16:13
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 银行卡管理")
@RequestMapping("/adminBanks")
public class AdminBankController {
	private final AdminBankService adminBankService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "bankCard", value = "银行卡号"),
			}
	)
	@PreAuthorize("hasAuthority('admin_bank_query')")
	public R<Page<AdminBank>> findByPage(@ApiIgnore Page<AdminBank> page, String bankCard) {
		return R.ok(adminBankService.findByPage(page, bankCard));
	}

	@ApiOperation(value = "新增银行卡")
	@PostMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "adminBank", value = "adminBank 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('admin_bank_create')")
	public R add(@RequestBody @Validated AdminBank adminBank) {
		boolean save = adminBankService.save(adminBank);
		if (save) {
			return R.ok("新增成功");
		}
		return R.fail("新增失败");
	}

	@ApiOperation(value = "修改银行卡")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "adminBank", value = "adminBank 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('admin_bank_update')")
	public R update(@RequestBody @Validated AdminBank adminBank) {
		boolean update = adminBankService.updateById(adminBank);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}

	@ApiOperation(value = "修改银行卡状态")
	@PostMapping("/adminUpdateBankStatus")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "bankId", value = "银行卡 id"),
					@ApiImplicitParam(name = "status", value = "要修改的状态"),
			}
	)
	@PreAuthorize("hasAuthority('admin_bank_update')")
	public R updateBankStatus(Long bankId, Byte status) {
		AdminBank adminBank = new AdminBank();
		adminBank.setId(bankId);
		adminBank.setStatus(status);
		boolean update = adminBankService.updateById(adminBank);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}
}
