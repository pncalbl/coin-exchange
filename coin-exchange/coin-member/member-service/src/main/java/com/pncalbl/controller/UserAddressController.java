package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.UserAddress;
import com.pncalbl.model.R;
import com.pncalbl.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author pncalbl
 * @date 2021/10/23 15:19
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "会员: 用户钱包地址管理")
@RequestMapping("/userAddress")
public class UserAddressController {

	private final UserAddressService userAddressService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "userId", value = "用户 id"),
			}
	)
	@PreAuthorize("hasAuthority('user_wallet_query')")
	public R<Page<UserAddress>> findByPage(@ApiIgnore Page<UserAddress> page, Long userId) {
		page.addOrder(OrderItem.desc("last_update_time"));
		return R.ok(userAddressService.findByPage(page, userId));
	}
}
