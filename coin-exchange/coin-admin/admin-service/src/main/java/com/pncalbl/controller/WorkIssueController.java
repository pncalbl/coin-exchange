package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.WorkIssue;
import com.pncalbl.model.R;
import com.pncalbl.service.WorkIssueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author pncalbl
 * @date 2021/10/21 15:38
 * @e-mail pncalbl@qq.com
 * @description 后台: 客户工单
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "后台: 客户工单管理")
@RequestMapping("/workIssues")
public class WorkIssueController {

	private final WorkIssueService workIssueService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "status", value = "工单状态"),
					@ApiImplicitParam(name = "startTime", value = "开始时间"),
					@ApiImplicitParam(name = "endTime", value = "结束时间"),
			}
	)
	@PreAuthorize("hasAuthority('work_issue_query')")
	public R<Page<WorkIssue>> findByPage(@ApiIgnore Page<WorkIssue> page, Integer status, String startTime, String endTime) {
		page.addOrder(OrderItem.desc("last_update_time"));
		return R.ok(workIssueService.findByPage(page, status, startTime, endTime));
	}

	@ApiOperation(value = "回复工单")
	@PatchMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "id", value = "工单 id"),
					@ApiImplicitParam(name = "answer", value = "回复内容"),
			}
	)
	@PreAuthorize("hasAuthority('work_issue_update')")
	public R update(Long id, String answer) {
		WorkIssue workIssue = new WorkIssue();
		workIssue.setId(id);
		workIssue.setAnswer(answer);
		// 设置回复人 id
		String userId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		workIssue.setAnswerUserId(Long.valueOf(userId));
		boolean b = workIssueService.updateById(workIssue);
		if (b) {
			return R.ok("回复成功");
		}
		return R.fail("回复失败");
	}
}
