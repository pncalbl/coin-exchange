package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.AdminBank;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/21 16:12
 * @e-mail pncalbl@qq.com
 * @description 银行卡管理服务接口
 **/
public interface AdminBankService extends IService<AdminBank> {


	/**
	 * @param page     分页参数
	 * @param bankCard 银行卡号
	 * @return 分页数据
	 */
	Page<AdminBank> findByPage(Page<AdminBank> page, String bankCard);
}
