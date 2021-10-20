package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.mapper.WebConfigMapper;
import com.pncalbl.domain.WebConfig;
import com.pncalbl.service.WebConfigService;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description 资源配置服务实现类
 **/
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService {

	/**
	 * 条件分页查询
	 *
	 * @param page 分页参数
	 * @param name webConfig 名称
	 * @param type webConfig 类型
	 * @return 分页数据
	 */
	@Override
	public Page<WebConfig> findByPage(Page<WebConfig> page, String name, String type) {
		return page(page, new LambdaQueryWrapper<WebConfig>()
				.like(!StringUtils.isEmpty(name), WebConfig::getName, name)
				.eq(!StringUtils.isEmpty(type), WebConfig::getType, type));
	}
}
