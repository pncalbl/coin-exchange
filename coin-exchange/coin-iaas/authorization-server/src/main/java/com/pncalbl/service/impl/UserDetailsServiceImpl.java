package com.pncalbl.service.impl;

import com.pncalbl.constant.LoginConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pncalbl
 * @date 2021/10/12 19:46
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		assert requestAttributes != null;
		String loginType = requestAttributes.getRequest().getParameter("login_type");   // 区分后台人员还是普通用户登录
		if (StringUtils.isEmpty(loginType)) {
			throw new AuthenticationServiceException("登录类型不能为 null!");
		}

		UserDetails userDetails;

		try {
			String grant_type = requestAttributes.getRequest().getParameter("grant_type");
			if (LoginConstant.REFRESH_TYPE.equals(grant_type)) {    // 当使用 refresh_token 方式授权时
				username = adjustUsername(username, loginType);    // 纠正 username 为真正的 username , 之前为 id
			}
			switch (loginType) {
				case LoginConstant.ADMIN_TYPE:
					userDetails = loadSysUserByUsername(username);
					break;
				case LoginConstant.MEMBER_TYPE:
					userDetails = loadMemberUserByUsername(username);
					break;
				default:
					throw new AuthenticationServiceException("暂不支持的登录方式: " + loginType);
			}
		} catch (IncorrectResultSizeDataAccessException e) {    // 我们的用户不存在
			throw new UsernameNotFoundException("用户名" + username + "不存在!");
		}
		return userDetails;
	}

	/**
	 * 纠正用户的名称
	 *
	 * @param username  用户 id
	 * @param loginType 登录类型
	 * @return 纠正后的用户名
	 */
	private String adjustUsername(String username, String loginType) {
		if (LoginConstant.ADMIN_TYPE.equals(loginType)) {
			return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
		}
		if (LoginConstant.MEMBER_TYPE.equals(loginType)) {
			return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
		}
		return username;
	}


	/**
	 * 后台管理人员的登录
	 *
	 * @param username 用户名
	 * @return 用户详细信息
	 */
	private UserDetails loadSysUserByUsername(String username) {
		// 1. 使用用户名查询用户
		return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, (resultSet, i) -> {
			if (resultSet.wasNull()) {
				throw new UsernameNotFoundException("用户名" + username + "不存在!");
			}
			long id = resultSet.getLong("id");
			String password = resultSet.getString("password");
			int status = resultSet.getInt("status");
			// 3. 封装成一个 UserDetails 对象, 并返回
			return new User(
					String.valueOf(id), // 使用 id -> username
					password,
					status == 1,
					true,
					true,
					true,
					getSysUserPermission(id)    // 2. 查询用户对应的权限
			);
		}, username);
	}

	/**
	 * 通过用户 id 查询用户对应的权限
	 *
	 * @param id 用户 id
	 * @return 用户权限的集合
	 */
	private Collection<? extends GrantedAuthority> getSysUserPermission(long id) {
		String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
		List<String> permission; // 用户权限的集合

		// 1. 当用户为超级管理员是, 他拥有所有权限
		if (LoginConstant.ADMIN_ROLE_CODE.equals(roleCode)) {
			permission = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
		} else { // 2. 普通用户, 需要使用 角色 - 权限表 查询
			permission = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSIONS_SQL, String.class, id);
		}
		if (permission.isEmpty()) {
			return Collections.emptySet();
		}
		return permission.stream()
				.distinct()   // 去重
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	/**
	 * 普通会员用户的登录
	 *
	 * @param username 用户名
	 * @return 用户详细信息
	 */
	private UserDetails loadMemberUserByUsername(String username) {
		return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, (resultSet, rowNum) -> {
			if (resultSet.wasNull()) {
				throw new UsernameNotFoundException("用户名" + username + "不存在!");
			}
			long id = resultSet.getLong("id");
			String password = resultSet.getString("password");
			int status = resultSet.getInt("status");
			// 3. 封装成一个 UserDetails 对象, 并返回
			return new User(
					String.valueOf(id), // 使用 id -> username
					password,
					status == 1,
					true,
					true,
					true,
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))    // 2. 查询用户对应的权限
			);
		}, username, username);
	}

}
