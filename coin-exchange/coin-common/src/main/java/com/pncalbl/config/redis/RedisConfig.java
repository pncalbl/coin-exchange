package com.pncalbl.config.redis;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author pncalbl
 * @date 2021/10/13 20:25
 * @e-mail pncalbl@qq.com
 * @description
 **/

@Configuration
public class RedisConfig {
	/**
	 * RedisTemplate
	 */
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		// redis key 的序列化
		StringRedisSerializer keyRedisSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(keyRedisSerializer);
		redisTemplate.setHashKeySerializer(keyRedisSerializer);
		// redis value 的序列化
		GenericJackson2JsonRedisSerializer valuesRedisSerializer = new GenericJackson2JsonRedisSerializer();
		redisTemplate.setValueSerializer(valuesRedisSerializer);
		redisTemplate.setHashValueSerializer(valuesRedisSerializer);
		return redisTemplate;
	}
}
