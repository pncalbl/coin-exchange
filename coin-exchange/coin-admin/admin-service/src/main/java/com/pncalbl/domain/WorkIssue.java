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
 * @description 工单记录
 **/

@ApiModel(value = "com-pncalbl-domain-WorkIssue")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "work_issue")
public class WorkIssue {
	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.INPUT)
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 用户id(提问用户id)
	 */
	@TableField(value = "user_id")
	@ApiModelProperty(value = "用户id(提问用户id)")
	private Long userId;

	/**
	 * 回复人id
	 */
	@TableField(value = "answer_user_id")
	@ApiModelProperty(value = "回复人id")
	private Long answerUserId;

	/**
	 * 回复人名称
	 */
	@TableField(value = "answer_name")
	@ApiModelProperty(value = "回复人名称")
	private String answerName;

	/**
	 * 工单内容
	 */
	@TableField(value = "question")
	@ApiModelProperty(value = "工单内容")
	private String question;

	/**
	 * 回答内容
	 */
	@TableField(value = "answer")
	@ApiModelProperty(value = "回答内容")
	private String answer;

	/**
	 * 状态：1-待回答；2-已回答；
	 */
	@TableField(value = "`status`")
	@ApiModelProperty(value = "状态：1-待回答；2-已回答；")
	private Integer status;

	/**
	 * 修改时间
	 */
	@TableField(value = "last_update_time", fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty(value = "修改时间")
	private Date lastUpdateTime;

	/**
	 * 创建时间
	 */
	@TableField(value = "created", fill = FieldFill.INSERT)
	@ApiModelProperty(value = "创建时间")
	private Date created;

	@TableField(exist = false)
	@ApiModelProperty(value = "工单的创建者用户名")
	private String username = "测试用户";

	@TableField(exist = false)
	@ApiModelProperty(value = "工单创建者的真实名称")
	private String realName = "测试用户";
}