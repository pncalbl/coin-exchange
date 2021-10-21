package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.Config;
import com.pncalbl.mapper.ConfigMapper;
import com.pncalbl.service.ConfigService;
import org.springframework.util.StringUtils;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

	/**
	 * 条件分页查询
	 *
	 * @param page 分页参数
	 * @param type 参数类型
	 * @param code 参数 code
	 * @param name 参数名称
	 * @return 分页数据
	 */
	@Override
	public Page<Config> findByPage(Page<Config> page, String type, String code, String name) {
		return page(page, new LambdaQueryWrapper<Config>()
				.like(!StringUtils.isEmpty(type), Config::getType, type)
				.like(!StringUtils.isEmpty(code), Config::getCode, code)
				.like(!StringUtils.isEmpty(name), Config::getName, name));
	}
}
