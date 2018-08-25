package com.gzsoftware.pet.entity.vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;


/**
 * Redis Cache Impl
 * @author pango leung
 *
 */
public class RedisCache implements Cache {

	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
	private static JedisConnectionFactory jedisConnectionFactory;

	private final String id;

	/**
	 * The {@code ReadWriteLock}.
	 */
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	// key and value serialier.
	private final RedisSerializer<Object> defSer = new JdkSerializationRedisSerializer();
	private final RedisSerializer<String> strSer = new StringRedisSerializer();
	private final GenericJackson2JsonRedisSerializer jsonSer = new GenericJackson2JsonRedisSerializer();

	/**
	 * redis cache constructor
	 * @param id
	 */
	public RedisCache(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		String prefix = "(" + id + ")";
		logger.info(prefix +  " Constructed Mybatis Redis Cache ");
		this.id = id;
	}
	
	/**
	 * return redis cache id
	 */
	@Override
	public String getId() {
		return this.id;
	}


	/**
	 * clear all Redis data
	 */
	@Override
	public void clear() {
		JedisConnection connection = null;
		String prefix = "(" + this.id + ")";
		try {
			logger.debug(prefix + " Clear Mybatis Redis Cache");
			connection = jedisConnectionFactory.getConnection();
			connection.flushDb();
			connection.flushAll();
		} catch (JedisConnectionException e) {
			logger.error(prefix + " Clear Mybatis Redis Cache Fail: " + e.getMessage());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}


	/**
	 * Save by key and value
	 */
	@Override
	public void putObject(Object key, Object value) {
		String prefix = "(" + this.id + ")";
		JedisConnection connection = null;
		
		if(value == null) {
			logger.error(prefix + " Put Object Key: " + key.toString() + " , value is null!");
			return;
		}
		
		try {
			logger.debug(prefix + " Put Object Key: " + key.toString());
			logger.debug(prefix + " Put Object Value: " + value.toString());
			
			byte[] keyByte = strSer.serialize(key.toString());
			byte[] valueByte = jsonSer.serialize(value);
			connection = jedisConnectionFactory.getConnection();
			connection.set(keyByte, valueByte);
		} catch (JedisConnectionException e) {
			logger.error(prefix + " Put Object Fail: " + e.toString());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	/**
	 * return object via key
	 */
	@Override
	public Object getObject(Object key) {
		Object result = null;
		JedisConnection connection = null;
		String prefix = "(" + this.id + ")";
		try {
			logger.debug(prefix + " Get Object Key: " + key.toString());
			byte[] keyByte = strSer.serialize(key.toString());
			connection = jedisConnectionFactory.getConnection();
			result = jsonSer.deserialize(connection.get(keyByte));
			if (result != null) {
				logger.debug(prefix + " Get Object Result: " + result.toString());
			}
		} catch (JedisConnectionException e) {
			logger.error(prefix + " Get Object Fail: " + e.toString());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}
	
	/**
	 * Remove Object via key
	 */
	@Override
	public Object removeObject(Object key) {
		JedisConnection connection = null;
		Object result = null;
		String prefix = "(" + this.id + ")";
		try {
			logger.info(prefix + " Remove Object Key: " + key.toString());
			
			byte[] keyByte = strSer.serialize(key.toString());
			connection = jedisConnectionFactory.getConnection();
			result = connection.expire(keyByte, 0);
		} catch (JedisConnectionException e) {
			logger.error(prefix + " Remove Object Fail: " + e.toString());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}


	@Override
	public ReadWriteLock getReadWriteLock() {
		return this.readWriteLock;
	}

	@Override
	public int getSize() {
		int result = 0;
		JedisConnection connection = null;
		try {
			connection = jedisConnectionFactory.getConnection();
			result = Integer.valueOf(connection.dbSize().toString());
		} catch (JedisConnectionException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return result;
	}

	

	public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory) {
		RedisCache.jedisConnectionFactory = jedisConnectionFactory;
	}
}
