package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.UserBank;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
public interface UserBankService extends IService<UserBank> {


	/**
	 * 条件分页查询
	 *
	 * @param page  分页参数
	 * @param usrId 用户 id
	 * @return 分页数据
	 */
	Page<UserBank> findByPage(Page<UserBank> page, Long usrId);
}
