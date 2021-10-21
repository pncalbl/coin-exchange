package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.mapper.AdminBankMapper;
import com.pncalbl.domain.AdminBank;
import com.pncalbl.service.AdminBankService;
import org.springframework.util.StringUtils;

/**
 * @author pncalbl
 * @date 2021/10/21 16:12
 * @e-mail pncalbl@qq.com
 * @description 银行卡管理服务实现类
 **/
@Service
public class AdminBankServiceImpl extends ServiceImpl<AdminBankMapper, AdminBank> implements AdminBankService {

	@Override
	public Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard) {
		return page(page, new LambdaQueryWrapper<AdminBank>()
				.like(!StringUtils.isEmpty(bankCard), AdminBank::getBankCard, bankCard));
	}
}
