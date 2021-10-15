package com.pncalbl.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
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
	@Bean
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

	/**
	 * 更换redis的序列化形式为Jackson
	 *
	 * @param objectMapper 对象映射
	 * @return 序列化结果
	 */
	@Bean
	public RedisSerializer<Object> redisSerializer(ObjectMapper objectMapper) {
		//创建JSON序列化器
		Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
		serializer.setObjectMapper(objectMapper);
		return serializer;
	}

}
