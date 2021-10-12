package com.pncalbl.constant;

/**
 * @author pncalbl
 * @date 2021/10/12 19:41
 * @e-mail pncalbl@qq.com
 * @description 登录的常量
 **/

public class LoginConstant {
	/**
	 * 后台管理人员
	 */
	public static final String ADMIN_TYPE = "admin_type";
	/**
	 * 普通会员用户
	 */
	public static final String MEMBER_TYPE = "member_type";

	/**
	 * 通过用户名查询后台管理人员
	 */
	public static final String QUERY_ADMIN_SQL =
			"SELECT `id`,`username`,`password`,`status` FROM sys_user WHERE username = ?";

	/**
	 * 查询后台管理人员的角色 code
	 */
	public static final String QUERY_ROLE_CODE_SQL =
			"SELECT `code` FROM sys_role LEFT JOIN sys_user_role " +
					"ON sys_role.id = sys_user_role.role_id WHERE sys_user_role.user_id = ? ";


	/**
	 * 查询超级管理员的所有权限
	 */
	public static final String QUERY_ALL_PERMISSIONS =
			"SELECT `name` FROM sys_privilege";


	/**
	 * 查询后台管理人员的权限
	 */
	public static final String QUERY_PERMISSIONS_SQL =
			"SELECT `name` FROM sys_privilege LEFT JOIN sys_role_privilege ON " +
					"sys_role_privilege.privilege_id = sys_privilege.id LEFT JOIN sys_user_role ON " +
					"sys_role_privilege.role_id = sys_user_role.role_id WHERE sys_user_role.user_id = ? ";

	/**
	 * 超级管理员的 roleCode
	 */
	public static final String ADMIN_ROLE_CODE = "ROLE_ADMIN";

	/**
	 * 普通会员用户的查询
	 */
	public static final String QUERY_MEMBER_SQL =
			"SELECT `id`, `password`, `status` FROM `user` " +
					"WHERE mobile = ? or email = ?";
}
