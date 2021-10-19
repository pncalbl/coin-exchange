package com.pncalbl.controller;

import com.pncalbl.domain.SysMenu;
import com.pncalbl.model.R;
import com.pncalbl.model.RolePrivilegesParam;
import com.pncalbl.service.SysRolePrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/18 17:38
 * @e-mail pncalbl@qq.com
 * @description 角色权限的配置
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 角色权限的配置")
public class SysRolePrivilegeController {

	private final SysRolePrivilegeService sysRolePrivilegeService;

	@ApiOperation(value = "查询角色的权限列表")
	@GetMapping("/roles_privileges")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "roleId", value = "角色的 ID"),
			}
	)
	public R<List<SysMenu>> findSysMenuAndPrivileges(Long roleId) {
		List<SysMenu> sysMenus = sysRolePrivilegeService.findSysMenuAndPrivileges(roleId);
		return R.ok(sysMenus);
	}

	@ApiOperation(value = "授予角色某种权限")
	@PostMapping("/grant_privileges")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "rolePrivilegesParam", value = "rolePrivilegesParam 的 Json 数据"),
			}
	)
	public R grantPrivileges(@RequestBody RolePrivilegesParam rolePrivilegesParam) {
		boolean isOK = sysRolePrivilegeService.grantPrivileges(rolePrivilegesParam);
		if (isOK) {
			return R.ok();
		}
		return R.fail("授予失败");
	}
}
