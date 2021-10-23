package com.pncalbl.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pncalbl.domain.UserAddress;
import com.pncalbl.domain.UserWallet;
import com.pncalbl.model.R;
import com.pncalbl.service.UserWalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author pncalbl
 * @date 2021/10/23 15:34
 * @e-mail pncalbl@qq.com
 * @description
 **/

@RestController
@RequiredArgsConstructor
@Api(tags = "会员: 用户提币地址管理")
@RequestMapping("/userWallets")
public class UserWalletController {
	private final UserWalletService userWalletService;

	@ApiOperation(value = "条件分页查询")
	@GetMapping()
	@ApiImplicitParams(
			{
					@ApiImplicitParam(name = "current", value = "当前页"),
					@ApiImplicitParam(name = "size", value = "每页显示的大小"),
					@ApiImplicitParam(name = "userId", value = "用户 id"),
			}
	)
	public R<Page<UserWallet>> findByPage(@ApiIgnore Page<UserWallet> page, Long userId) {
		page.addOrder(OrderItem.desc("last_update_time"));
		return R.ok(userWalletService.findByPage(page, userId));
	}
}
