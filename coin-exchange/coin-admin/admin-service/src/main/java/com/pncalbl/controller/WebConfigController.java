package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.Notice;
import com.pncalbl.domain.WebConfig;
import com.pncalbl.model.R;
import com.pncalbl.service.WebConfigService;
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
import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/20 22:06
 * @e-mail pncalbl@qq.com
 * @description 资源配置
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 资源配置")
@RequestMapping("/webConfigs")
public class WebConfigController {

	private final WebConfigService webConfigService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "name", value = "webConfig 名称"),
					@ApiImplicitParam(name = "type", value = "webConfig 类型"),
			}
	)
	@PreAuthorize("hasAuthority('web_config_query')")
	public R<Page<WebConfig>> findByPage(@ApiIgnore Page<WebConfig> page, String name, String type) {
		return R.ok(webConfigService.findByPage(page, name, type));
	}

	@ApiOperation(value = "新增资源配置")
	@PostMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "webConfig", value = "webConfig 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('web_config_create')")
	public R add(@RequestBody @Validated WebConfig webConfig) {
		boolean save = webConfigService.save(webConfig);
		if (save) {
			return R.ok("新增成功");
		}
		return R.fail("新增失败");
	}

	@ApiOperation(value = "修改资源配置")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "webConfig", value = "webConfig 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('web_config_update')")
	public R update(@RequestBody @Validated WebConfig webConfig) {
		boolean update = webConfigService.updateById(webConfig);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}

	@PostMapping("/delete")
	@ApiOperation(value = "删除资源配置")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "要删除的数据的ids")
	})
	@PreAuthorize("hasAuthority('web_config_delete')")
	public R update(@RequestBody String[] ids) {
		if (ids == null || ids.length == 0) {
			return R.fail("删除时需要给删除数据的Id");
		}
		boolean b = webConfigService.removeByIds(Arrays.asList(ids));
		if (b) {
			return R.ok();
		}
		return R.fail("删除失败");
	}

}
