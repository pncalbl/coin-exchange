package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.Notice;
import com.pncalbl.model.R;
import com.pncalbl.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;

/**
 * @author pncalbl
 * @date 2021/10/20 19:50
 * @e-mail pncalbl@qq.com
 * @description 公告管理
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 公告管理")
@RequestMapping("/notices")
public class NoticeController {

	private final NoticeService noticeService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "title", value = "标题"),
					@ApiImplicitParam(name = "startTime", value = "开始时间"),
					@ApiImplicitParam(name = "endTime", value = "结束时间"),
					@ApiImplicitParam(name = "status", value = "状态"),
			}
	)
	@PreAuthorize("hasAuthority('notice_query')")
	public R<Page<Notice>> findByPage(@ApiIgnore Page<Notice> page, String title, String startTime, String endTime, Integer status) {
		// 查询时，我们将最近新增或修改过的数据优先展示 -> 排序 -> lastUpdateTime
		page.addOrder(OrderItem.desc("last_update_time"));
		return R.ok(noticeService.findByPage(page, title, startTime, endTime, status));
	}


	@ApiOperation(value = "删除公告")
	@PostMapping("/delete")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "ids", value = "公告的id数组"),
			}
	)
	@PreAuthorize("hasAuthority('notice_delete')")
	public R delete(@RequestBody String[] ids) {
		if (ids == null || ids.length == 0) {
			return R.fail("删除时需要提供id值!");
		}
		boolean b = noticeService.removeByIds(Arrays.asList(ids));
		if (b) {
			return R.ok("删除成功");
		}
		return R.ok("删除失败");
	}

	@ApiOperation(value = "修改公告状态")
	@PostMapping("/updateStatus")
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "公告的id"),
					@ApiImplicitParam(name = "status", value = "公告的状态"),
			}
	)
	@PreAuthorize("hasAuthority('notice_update')")
	public R updateStatus(Long id, Integer status) {
		Notice notice = new Notice();
		notice.setId(id);
		notice.setStatus(status);
		boolean b = noticeService.updateById(notice);   // 局部修改, 只修改不为空的值
		if (b) {
			return R.ok("修改成功");
		}
		return R.ok("修改失败");
	}

	@ApiOperation(value = "新增公告")
	@PostMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "notice", value = "notice 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('notice_create')")
	public R add(@RequestBody Notice notice) {
		notice.setStatus(1);
		boolean save = noticeService.save(notice);
		if (save) {
			return R.ok("新增成功");
		}
		return R.fail("新增失败");
	}

	@ApiOperation(value = "修改公告")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "notice", value = "notice 的 Json 数据"),
			}
	)
	@PreAuthorize("hasAuthority('notice_update')")
	public R update(@RequestBody Notice notice) {
		boolean update = noticeService.updateById(notice);
		if (update) {
			return R.ok("修改成功");
		}
		return R.fail("修改失败");
	}

}
