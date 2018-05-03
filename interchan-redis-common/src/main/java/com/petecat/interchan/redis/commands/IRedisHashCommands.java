package com.petecat.interchan.redis.commands;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:  IRedisHashCommands   
 * @Description:Redis hash操作   
 * @author: mhuang
 * @date:   2017年8月31日 上午11:48:42
 */
public interface IRedisHashCommands {

	/**
	 * 
	 * @Title: hset   
	 * @Description: 设置单个值
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * @return boolean
	 */
	boolean hset(String key,String field,Object value);
	
	/**
	 * 
	 * @Title: hget   
	 * @Description: 获取单个对key的字段的值 
	 * @param key
	 * @param field
	 * @return
	 * @return String
	 */
	String hget(String key,String field);
	
	/**
	 * 
	 * @Title: hmset   
	 * @Description: 根据键设置多个字段和值
	 * @param key 键
	 * @param params 多个值和字段（Map格式）
	 * @return
	 * @return boolean
	 */
	boolean hmset(String key,Map<String, Object> params);
	
	/**
	 * 
	 * @Title: hmget   
	 * @Description: 根获取这个key中选择的多个field值 
	 * @param key 获取的键
	 * @param fields 获取的字段列表
	 * @return
	 * @return Collection<String>
	 */
	Collection<String> hmget(String key,Collection<String> fields);
	/**
	 * 
	 * @Title: hgetall   
	 * @Description: 获取key的所有的字段、值
	 * @param key 建
	 * @return
	 * @return Map<String,String> 字段和值
	 */
	Map<String, String> hgetall(String key);
	
	///////////////////////////操作其他库////////////////////////////
	
	/**
	 * 
	 * @Title: hset   
	 * @Description: 设置单个值
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * @return boolean
	 */
	boolean hset(int index,String key,String field,Object value);
	
	
	/**
	 * 
	 * @Title: hget   
	 * @Description: 获取单个对key的字段的值 
	 * @param key
	 * @param field
	 * @return
	 * @return String
	 */
	String hget(int index, String key, String field);
	
	/**
	 * 
	 * @Title: hmset   
	 * @Description: 根据键设置多个字段和值
	 * @param key 键
	 * @param params 多个值和字段（Map格式）
	 * @return
	 * @return boolean
	 */
	boolean hmset(int index,String key,Map<String, Object> params);
	
	/**
	 * 
	 * @Title: hmget   
	 * @Description: 根获取这个key中选择的多个field值 
	 * @param key 获取的键
	 * @param fields 获取的字段列表
	 * @return
	 * @return Collection<String>
	 */
	Collection<String> hmget(int index,String key,Collection<String> fields);
	/**
	 * 
	 * @Title: hgetall   
	 * @Description: 获取key的所有的字段、值
	 * @param key 建
	 * @return
	 * @return Map<String,String> 字段和值
	 */
	Map<String, String> hgetall(int index,String key);
	
	List<String> hvals(String key);
	/**
	 * 
	 * @Title: hvals   
	 * @Description: 获取key所有的值
	 * @param index
	 * @param key
	 * @return
	 * @return List<String>
	 */
	List<String> hvals(int index,String key);
	
	Long hdel(int index,String key,Object field);
	
	Long hdel(String key,Object field);
	
	Long hincrby(String key,String field,Long incroment);
	
	Long hincrby(int index,String key,String field,Long incroment);
	
	List<String> hkeys(String key);
	
	List<String> hkeys(int index,String key);
	
}
