package com.pncalbl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.WebConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description 资源配置服务接口
 **/
public interface WebConfigService extends IService<WebConfig> {


	/**
	 * 条件分页查询
	 *
	 * @param page 分页参数
	 * @param name webConfig 名称
	 * @param type webConfig 类型
	 * @return 分页数据
	 */
	Page<WebConfig> findByPage(Page<WebConfig> page, String name, String type);
}
