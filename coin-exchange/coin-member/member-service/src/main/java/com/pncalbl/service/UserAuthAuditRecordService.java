package com.pncalbl.service;

import com.pncalbl.domain.UserAuthAuditRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author pncalbl
 * @date 2021/10/21 20:01
 * @e-mail pncalbl@qq.com
 * @description ${TAGES}
 **/
public interface UserAuthAuditRecordService extends IService<UserAuthAuditRecord> {


	/**
	 * 获取一个用户的审核记录
	 *
	 * @param id 用户 id
	 * @return 审核记录
	 */
	List<UserAuthAuditRecord> getUserAuthInfoByUserId(Long id);
}
