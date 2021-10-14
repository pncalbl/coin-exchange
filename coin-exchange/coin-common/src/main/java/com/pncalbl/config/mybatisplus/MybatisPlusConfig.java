package com.pncalbl.config.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.incrementer.IKeyGenerator;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author pncalbl
 * @date 2021/10/13 20:16
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Configuration
public class MybatisPlusConfig {

	/**
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setDbType(DbType.MYSQL);
		return paginationInterceptor;
	}

	/**
	 * 乐观锁
	 */
	public OptimisticLockerInterceptor optimisticLockerInterceptor() {
		OptimisticLockerInterceptor optimisticLockerInterceptor = new OptimisticLockerInterceptor();
		return optimisticLockerInterceptor;
	}

	/**
	 * Id 生成器-->
	 * <p>
	 * 特殊的一些类使用
	 * 默认使用
	 *
	 * @return 主键生成器
	 */
	@Bean
	public IKeyGenerator iKeyGenerator() {
		H2KeyGenerator h2KeyGenerator = new H2KeyGenerator();
		return h2KeyGenerator;
	}


}
