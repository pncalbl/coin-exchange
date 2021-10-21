package com.pncalbl.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.AdminBank;
import com.pncalbl.domain.Config;
import com.pncalbl.model.R;
import com.pncalbl.service.ConfigService;
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
 * @date 2021/10/21 17:05
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 系统参数配置")
@RequestMapping("/configs")
public class ConfigController {

	private final ConfigService configService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "type", value = "参数类型"),
					@ApiImplicitParam(name = "code", value = "参数 code"),
					@ApiImplicitParam(name = "name", value = "参数名称"),
			}
	)
	@PreAuthorize("hasAuthority('config_query')")
	public R<Page<Config>> findByPage(@ApiIgnore Page<Config> page, String type, String code, String name) {
		return R.ok(configService.findByPage(page, type, code, name));
	}

	@ApiOperation(value = "新增参数配置")
	@PostMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "config", value = "config 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('config_create')")
	public R add(@RequestBody @Validated Config config) {
		boolean save = configService.save(config);
		if (save) {
			return R.ok("新增成功");
		}
		return R.fail("新增失败");
	}

	@ApiOperation(value = "修改参数配置")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "config", value = "config 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('config_update')")
	public R update(@RequestBody @Validated Config config) {

		boolean update = configService.updateById(config);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}


}
