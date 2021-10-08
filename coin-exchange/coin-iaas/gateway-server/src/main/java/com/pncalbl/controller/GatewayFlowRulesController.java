package com.pncalbl.controller;

import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author pncalbl
 * @date 2021/10/8 22:40
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
public class GatewayFlowRulesController {

	/**
	 * 获取当前系统的限流策略
	 *
	 * @return 限流策略集合
	 */
	@GetMapping("/gw/flow/rules")
	public Set<GatewayFlowRule> getCurrentGatewayFlowRules() {
		return GatewayRuleManager.getRules();
	}

	/**
	 * 获取自定义的 api 分组
	 *
	 * @return api 分组集合
	 */
	@GetMapping("/gw/api/groups")
	public Set<ApiDefinition> getApiGroups() {
		return GatewayApiDefinitionManager.getApiDefinitions();
	}
}
