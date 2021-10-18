package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.SysRole;
import com.pncalbl.model.R;
import com.pncalbl.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/**
 * @author pncalbl
 * @date 2021/10/18 13:41
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 角色管理")
@RequestMapping("/roles")
public class SysRoleController {

	private final SysRoleService sysRoleService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "name", value = "角色名称"),
			}
	)
	@PreAuthorize("hasAuthority('sys_role_query')")
	public R<Page<SysRole>> findByPage(@ApiIgnore Page<SysRole> page, String name) {
		// 查询时，我们将最近新增或修改过的数据优先展示 -> 排序 -> lastUpdateTime
		page.addOrder(OrderItem.desc("last_update_time"));
		return R.ok(sysRoleService.findByPage(page, name));
	}


	@ApiOperation(value = "新增角色")
	@PostMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "SysRole", value = "SysRole 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('sys_role_create')")
	public R add(@RequestBody @Validated SysRole sysRole) {
		boolean save = sysRoleService.save(sysRole);
		if (save) {
			return R.ok("新增成功");
		}
		return R.fail("新增失败");
	}

	@ApiOperation(value = "删除角色")
	@PostMapping("/delete")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "ids", value = "要删除角色的id的集合"),
			}
	)
	@PreAuthorize("hasAuthority('sys_role_delete')")
	public R delete(@RequestBody String[] ids) {
		if (ids == null || ids.length == 0) {
			return R.fail("要删除的数据不能为null");
		}
		boolean b = sysRoleService.removeByIds(Arrays.asList(ids));
		if (b) {
			return R.ok("删除成功");
		}
		return R.fail("删除失败");
	}
}
