package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.Notice;
import com.pncalbl.mapper.NoticeMapper;
import com.pncalbl.service.NoticeService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

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
	@Override
	public Page<Notice> findByPage(Page<Notice> page, String title,
	                               String startTime, String endTime, Integer status) {
		return page(page, new LambdaQueryWrapper<Notice>()
				.like(!StringUtils.isEmpty(title), Notice::getTitle, title)
				.between(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime),
						Notice::getCreated, startTime, endTime + "23:59:59")
				.eq(status != null, Notice::getStatus, status));
	}
}
