package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.UserAuthAuditRecord;
import com.pncalbl.mapper.UserAuthAuditRecordMapper;
import com.pncalbl.service.UserAuthAuditRecordService;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
public class UserAuthAuditRecordServiceImpl extends ServiceImpl<UserAuthAuditRecordMapper, UserAuthAuditRecord> implements UserAuthAuditRecordService {

	/**
	 * 获取一个用户的审核记录
	 *
	 * @param id 用户 id
	 * @return 审核记录
	 */
	@Override
	public List<UserAuthAuditRecord> getUserAuthInfoByUserId(Long id) {
		return list(new LambdaQueryWrapper<UserAuthAuditRecord>()
				.eq(UserAuthAuditRecord::getUserId, id)
				.orderByDesc(UserAuthAuditRecord::getCreated)
				.last("limit 3"));
	}
}
