package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.SysPrivilege;
import com.pncalbl.model.R;
import com.pncalbl.service.SysPrivilegeService;
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
 * @date 2021/10/17 20:10
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 权限管理")
@RequestMapping("/privileges")
public class SysPrivilegeController {

	private final SysPrivilegeService sysPrivilegeService;

	@ApiOperation(value = "查询权限")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
			}
	)
	@PreAuthorize("hasAuthority('sys_privilege_query')")
	public R<Page<SysPrivilege>> findByPage(@ApiIgnore Page<SysPrivilege> page) {
		// 查询时，我们将最近新增或修改过的数据优先展示 -> 排序 -> lastUpdateTime
		page.addOrder(OrderItem.desc("last_update_time"));
		Page<SysPrivilege> sysPrivilegePage = sysPrivilegeService.page(page);
		return R.ok(sysPrivilegePage);
	}

	@ApiOperation(value = "新增权限")
	@PostMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "sysPrivilege", value = "sysPrivilege 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('sys_privilege_create')")
	public R add(@RequestBody @Validated SysPrivilege sysPrivilege) {
		boolean save = sysPrivilegeService.save(sysPrivilege);
		if (save) {
			return R.ok("新增成功");
		}
		return R.fail("新增失败");
	}

	@ApiOperation(value = "修改权限")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "sysPrivilege", value = "sysPrivilege 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('sys_privilege_update')")
	public R update(@RequestBody @Validated SysPrivilege sysPrivilege) {
		boolean update = sysPrivilegeService.updateById(sysPrivilege);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}
}
