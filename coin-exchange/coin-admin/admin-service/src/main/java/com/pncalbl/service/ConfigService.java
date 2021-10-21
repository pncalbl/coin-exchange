package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.Config;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
public interface ConfigService extends IService<Config> {


	/**
	 * 条件分页查询
	 *
	 * @param page 分页参数
	 * @param type 参数类型
	 * @param code 参数 code
	 * @param name 参数名称
	 * @return 分页数据
	 */
	Page<Config> findByPage(Page<Config> page, String type, String code, String name);
}
