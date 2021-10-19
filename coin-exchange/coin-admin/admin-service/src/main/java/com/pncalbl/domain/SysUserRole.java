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
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description 用户角色配置
 **/

@ApiModel(value = "com-pncalbl-domain-SysUserRole")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user_role")
public class SysUserRole {
	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.INPUT)
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 角色ID
	 */
	@TableField(value = "role_id")
	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	/**
	 * 用户ID
	 */
	@TableField(value = "user_id")
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	/**
	 * 创建人
	 */
	@TableField(value = "create_by")
	@ApiModelProperty(value = "创建人")
	private Long createBy;

	/**
	 * 修改人
	 */
	@TableField(value = "modify_by", fill = FieldFill.UPDATE)
	@ApiModelProperty(value = "修改人")
	private Long modifyBy;

	/**
	 * 创建时间
	 */
	@TableField(value = "created", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private Date created;

	/**
	 * 修改时间
	 */
	@TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "修改时间")
	private Date lastUpdateTime;
}