package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.UserAddress;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.UserWallet;
import com.pncalbl.mapper.UserWalletMapper;
import com.pncalbl.service.UserWalletService;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletMapper, UserWallet> implements UserWalletService {

	/**
	 * 条件分页查询
	 *
	 * @param page   分页参数
	 * @param userId 用户 id
	 * @return 分页数据
	 */
	@Override
	public Page<UserWallet> findByPage(Page<UserWallet> page, Long userId) {
		return page(page, new LambdaQueryWrapper<UserWallet>()
				.eq(UserWallet::getUserId, userId));
	}
}
