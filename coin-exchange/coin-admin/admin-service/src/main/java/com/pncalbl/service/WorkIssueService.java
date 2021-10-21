package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.WorkIssue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description 客户工单服务接口
 **/
public interface WorkIssueService extends IService<WorkIssue> {


	/**
	 * 条件分页查询
	 *
	 * @param page      分页参数
	 * @param status    工单状态
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @return 分页数据
	 */
	Page<WorkIssue> findByPage(Page<WorkIssue> page, Integer status, String startTime, String endTime);
}
