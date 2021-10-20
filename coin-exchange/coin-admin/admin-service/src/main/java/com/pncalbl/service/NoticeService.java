package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
public interface NoticeService extends IService<Notice> {


	/**
	 * 条件分页查询
	 *
	 * @param page      分页参数
	 * @param title     标题
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @param status    状态
	 * @return 分页数据
	 */
	Page<Notice> findByPage(Page<Notice> page, String title, String startTime, String endTime, Integer status);
}
