package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.Notice;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.WorkIssue;
import com.pncalbl.mapper.WorkIssueMapper;
import com.pncalbl.service.WorkIssueService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description 客户工单服务实现类
 **/
@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements WorkIssueService {

	/**
	 * 条件分页查询
	 *
	 * @param page      分页参数
	 * @param status    工单状态
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @return 分页数据
	 */
	@Override
	public Page<WorkIssue> findByPage(Page<WorkIssue> page, Integer status, String startTime, String endTime) {
		return page(page, new LambdaQueryWrapper<WorkIssue>()
				.eq(status != null, WorkIssue::getStatus, status)
				.between(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime),
						WorkIssue::getCreated, startTime, endTime + "23:59:59"));
	}
}
