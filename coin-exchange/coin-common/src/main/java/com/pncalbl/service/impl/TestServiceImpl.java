package com.pncalbl.service.impl;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.pncalbl.model.WebLog;
import com.pncalbl.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author pncalbl
 * @date 2021/10/15 0:34
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Service
public class TestServiceImpl implements TestService {

	/**
	 * 通过用户名查询 weblog
	 *
	 * @param username 用户名
	 * @return weblog
	 */
	@Override
	@Cached(name = "com.pncalbl.service.impl.TestServiceImpl:", key = "#username", cacheType = CacheType.BOTH)
	public WebLog get(String username) {
		WebLog webLog = new WebLog();
		webLog.setUsername(username);
		webLog.setResult("ok");
		return webLog;
	}
}
