package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.SysPrivilege;
import com.pncalbl.domain.SysUser;
import com.pncalbl.model.R;
import com.pncalbl.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/**
 * @author pncalbl
 * @date 2021/10/19 20:01
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 员工管理")
@RequestMapping("/users")
public class SysUserController {

	private final SysUserService sysUserService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "mobile", value = "员工的手机号码"),
					@ApiImplicitParam(name = "fullname", value = "员工的全名"),
			}
	)
	@PreAuthorize("hasAuthority('sys_user_query')")
	public R<Page<SysUser>> findByPage(@ApiIgnore Page<SysUser> page, String mobile, String fullname) {
		page.addOrder(OrderItem.desc("last_update_time"));
		Page<SysUser> pageData = sysUserService.findByPage(page, mobile, fullname);
		return R.ok(pageData);
	}

	@ApiOperation(value = "新增员工")
	@PostMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "sysUser", value = "sysUser 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('sys_user_create')")
	public R add(@RequestBody @Validated SysUser sysUser) {
		String userId = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal().toString();
		sysUser.setCreateBy(Long.valueOf(userId));
		boolean isOk = sysUserService.addUser(sysUser);
		if (isOk) {
			return R.ok("新增成功");
		}
		return R.fail("新增失败");
	}

	@ApiOperation(value = "修改员工")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "sysUser", value = "sysUser 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('sys_user_update')")
	public R update(@RequestBody @Validated SysUser sysUser) {
		boolean update = sysUserService.updateById(sysUser);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}


	@ApiOperation(value = "删除员工")
	@PostMapping("/delete")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "ids", value = "用户的 ids"),
			}
	)
	@PreAuthorize("hasAuthority('sys_user_delete')")
	public R delete(@RequestBody Long ids[]) {
		boolean b = sysUserService.removeByIds(Arrays.asList(ids));
		if (b) {
			return R.ok("删除成功");
		}
		return R.fail("删除失败");
	}

}
