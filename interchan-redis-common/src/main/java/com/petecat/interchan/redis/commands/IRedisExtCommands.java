package com.petecat.interchan.redis.commands;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:  IRedisCommands   
 * @Description:通用Redis接口
 * @author: mhuang
 * @date:   2017年8月31日 下午1:46:51
 */
public interface IRedisExtCommands extends IRedisHashCommands, IRedisListCommands,
		IRedisSortedSetCommands, IRedisStringCommands,IRedisKeyCommands {
	
	/**
	 * 
	 * @Title: get   
	 * @Description: 获取
	 * @param key
	 * @param clazz
	 * @return
	 * @return T
	 */
	<T> T get(String key,Class<T> clazz);
	
	/**
	 * 
	 * @Title: get   
	 * @Description: 获取
	 * @param dbIndex
	 * @param key
	 * @param clazz
	 * @return
	 * @return T
	 */
	<T> T get(int dbIndex,String key,Class<T> clazz);
	/**
	 * 
	 * @Title: hset   
	 * @Description: 设置hash过期时间   
	 * @param key
	 * @param field
	 * @param value
	 * @param seconds
	 * @return
	 * @return boolean
	 */
	boolean hset(String key,String field,Object value,long seconds);
	
	/**
	 * 
	 * @Title: hget   
	 * @Description: 获取值转相应类型（该方法只支持单个）
	 * @param key
	 * @param field
	 * @param clazz
	 * @return
	 * @return T
	 */
	<T> T hget(String key,String field,Class<T> clazz);
	
	/**
	 * 
	 * @Title: hget   
	 * @Description: 获取值转相应类型（该方法只支持单个）
	 * @param key
	 * @param field
	 * @param clazz
	 * @return
	 * @return T
	 */
	<T> T hget(int dbIndex,String key,String field,Class<T> clazz);
	/**
	 * 
	 * @Title: hgetList   
	 * @Description: 获取值转相应类型（该方法只支持单个）
	 * @param key
	 * @param field
	 * @param clazz
	 * @return
	 * @return List<T>
	 */
	<T> List<T> hgetList(String key,String field,Class<T> clazz);
	
	<T> List<T> hgetList(int dbIndex,String key,String field,Class<T> clazz);
	
	<T> Map<String, T> hgetAll(int index,String key,Class<T> clazz);
	/**
	 * 
	 * @Title: hvals   
	 * @Description: 获取key所有的值
	 * @param index
	 * @param key
	 * @return
	 * @return List<String>
	 */
	<T> List<T> hvals(int index,String key,Class<T> clazz);
}
