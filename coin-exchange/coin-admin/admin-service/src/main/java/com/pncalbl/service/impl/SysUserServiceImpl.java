package com.pncalbl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pncalbl.domain.SysUser;
import com.pncalbl.domain.SysUserRole;
import com.pncalbl.mapper.SysUserMapper;
import com.pncalbl.service.SysUserRoleService;
import com.pncalbl.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pncalbl
 * @date 2021/10/15 20:37
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private final SysUserRoleService sysUserRoleService;

	/**
	 * 条件分页查询
	 *
	 * @param page     分页参数
	 * @param mobile   员工的手机号码
	 * @param fullname 员工的全名
	 * @return 分页数据
	 */
	@Override
	public Page<SysUser> findByPage(Page<SysUser> page, String mobile, String fullname) {
		Page<SysUser> pageData = page(page, new LambdaQueryWrapper<SysUser>()
				.like(!StringUtils.isEmpty(mobile), SysUser::getMobile, mobile)
				.like(!StringUtils.isEmpty(fullname), SysUser::getFullname, fullname));
		List<SysUser> records = pageData.getRecords();
		if (!CollectionUtils.isEmpty(records)) {
			for (SysUser record : records) {
				List<SysUserRole> userRoles = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>()
						.eq(SysUserRole::getUserId, record.getId()));
				if (!CollectionUtils.isEmpty(userRoles)) {
					record.setRole_strings(userRoles.stream()
							.map(sysUserRole -> sysUserRole.getRoleId().toString())
							.collect(Collectors.joining(",")));
				}
			}
		}
		return pageData;
	}

	/**
	 * 新增员工
	 *
	 * @param sysUser 员工数据
	 * @return 是否新增成功
	 */
	@Override
	@Transactional
	public boolean addUser(SysUser sysUser) {
		// 1. 用户密码
		String password = sysUser.getPassword();
		// 2. 用户的角色 ids
		String roleStrings = sysUser.getRole_strings();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encode = passwordEncoder.encode(password);   // 密码加密
		sysUser.setPassword(encode);    // 设置密码
		boolean save = super.save(sysUser);
		if (save) {
			// 给用户新增权限信息
			if (!StringUtils.isEmpty(roleStrings)) {
				String[] roleIds = roleStrings.split(",");
				List<SysUserRole> sysUserRoleList = new ArrayList<>(roleIds.length);
				for (String roleId : roleIds) {
					SysUserRole sysUserRole = new SysUserRole();
					sysUserRole.setRoleId(Long.valueOf(roleId));
					sysUserRole.setUserId(sysUser.getId());
					sysUserRoleList.add(sysUserRole);
				}
				sysUserRoleService.saveBatch(sysUserRoleList);
			}
		}
		return save;
	}

	@Override
	public boolean removeByIds(Collection<? extends Serializable> idList) {
		boolean b = super.removeByIds(idList);
		sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, idList));
		return b;
	}
}
