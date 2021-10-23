package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description
 **/
public interface UserAddressService extends IService<UserAddress> {


	/**
	 * 条件分页查询
	 *
	 * @param page   分页参数
	 * @param userId 用户 id
	 * @return 分页数据
	 */
	Page<UserAddress> findByPage(Page<UserAddress> page, Long userId);
}
