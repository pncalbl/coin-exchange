package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.SysUserLog;
import com.pncalbl.model.R;
import com.pncalbl.service.SysUserLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author pncalbl
 * @date 2021/10/19 21:48
 * @e-mail pncalbl@qq.com
 * @description 系统日志记录
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 系统日志记录")
@RequestMapping("/sysUserLog")
public class SysUserLogController {

	private final SysUserLogService sysUserLogService;

	@ApiOperation(value = "分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
			}
	)
	@PreAuthorize("hasAuthority('sys_user_log_query')")
	public R<Page<SysUserLog>> findByPage(@ApiIgnore Page<SysUserLog> page) {
		page.addOrder(OrderItem.desc("created"));
		return R.ok(sysUserLogService.page(page));
	}
}
