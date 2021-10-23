package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.mapper.UserBankMapper;
import com.pncalbl.domain.UserBank;
import com.pncalbl.service.UserBankService;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements UserBankService {

	/**
	 * 条件分页查询
	 *
	 * @param page  分页参数
	 * @param usrId 用户 id
	 * @return 分页数据
	 */
	@Override
	public Page<UserBank> findByPage(Page<UserBank> page, Long usrId) {
		return page(page, new LambdaQueryWrapper<UserBank>()
				.eq(usrId != null, UserBank::getUserId, usrId));
	}
}
