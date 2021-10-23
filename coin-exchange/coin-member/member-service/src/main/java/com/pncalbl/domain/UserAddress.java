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
 * @description 用户钱包地址信息
 **/

@ApiModel(value = "com-pncalbl-domain-UserAddress")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_address")
public class UserAddress {
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
	 * 地址
	 */
	@TableField(value = "address")
	@ApiModelProperty(value = "地址")
	private String address;

	/**
	 * keystore
	 */
	@TableField(value = "keystore")
	@ApiModelProperty(value = "keystore")
	private String keystore;

	/**
	 * 密码
	 */
	@TableField(value = "pwd")
	@ApiModelProperty(value = "密码")
	private String pwd;

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

	@TableField(value = "markid")
	@ApiModelProperty(value = "")
	private Long markid;

	@TableField(exist = false)
	@ApiModelProperty("币种名称")
	private String coinName = "测试币种";
}