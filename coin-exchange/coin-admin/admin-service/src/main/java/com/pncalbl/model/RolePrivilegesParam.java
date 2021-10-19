package com.pncalbl.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/18 21:14
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Data
@ApiModel(value = "接收角色和权限数据")
public class RolePrivilegesParam {

	@ApiModelProperty(value = "角色的 id")
	private Long roleId;

	@ApiModelProperty(value = "角色包含的权限")
	private List<Long> privilegeIds = Collections.emptyList();
}
