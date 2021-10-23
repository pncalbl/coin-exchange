package com.pncalbl.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description 用户提币地址
 **/

@ApiModel(value = "com-pncalbl-domain-UserWallet")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_wallet")
public class UserWallet {
	@TableId(value = "id", type = IdType.INPUT)
	@ApiModelProperty(value = "")
	private Long id;

	/**
	 * 用户ID
	 */
	@TableField(value = "user_id")
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	/**
	 * 币种ID
	 */
	@TableField(value = "coin_id")
	@ApiModelProperty(value = "币种ID")
	private Long coinId;

	/**
	 * 币种名称
	 */
	@TableField(value = "coin_name")
	@ApiModelProperty(value = "币种名称")
	private String coinName;

	/**
	 * 提币地址名称
	 */
	@TableField(value = "`name`")
	@ApiModelProperty(value = "提币地址名称")
	private String name;

	/**
	 * 地址
	 */
	@TableField(value = "addr")
	@ApiModelProperty(value = "地址")
	private String addr;

	/**
	 * 排序
	 */
	@TableField(value = "sort")
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 状态
	 */
	@TableField(value = "`status`")
	@ApiModelProperty(value = "状态")
	private Byte status;

	/**
	 * 更新时间
	 */
	@TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "更新时间")
	private Date lastUpdateTime;

	/**
	 * 创建时间
	 */
	@TableField(value = "created", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private Date created;
}