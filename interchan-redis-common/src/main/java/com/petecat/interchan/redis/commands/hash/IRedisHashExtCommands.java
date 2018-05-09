package com.petecat.interchan.redis.commands.hash;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:  IRedisHashExtCommands   
 * @Description:Redis hash扩展类
 * @author: mhuang
 * @date:   2018年5月9日 下午10:09:37
 */
public interface IRedisHashExtCommands {

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
	
	/**
	 * 
	 * @Title: hgetList   
	 * @Description: 获取值转相应类型（该方法只支持单个）
	 * @param dbIndex
	 * @param key
	 * @param field
	 * @param clazz
	 * @return
	 * @return List<T>
	 */
	<T> List<T> hgetList(int dbIndex,String key,String field,Class<T> clazz);
	
	/**
	 * 
	 * @Title: hgetAll   
	 * @Description: 获取key下的field value集合。
	 * @param key
	 * @param clazz
	 * @return
	 * @return Map<String,T>
	 */
	<T> Map<String, T> hgetAll(String key,Class<T> clazz);
	
	/**
	 * 
	 * @Title: hgetAll   
	 * @Description: 获取key下的field value集合。
	 * @param index
	 * @param key
	 * @param clazz
	 * @return
	 * @return Map<String,T>
	 */
	<T> Map<String, T> hgetAll(int index,String key,Class<T> clazz);
	
	/**
	 * 
	 * @Title: hgetAllList   
	 * @Description:  获取key下的field value集合。
	 * @param key
	 * @param clazz
	 * @return
	 * @return Map<String,List<T>>
	 */
	<T> Map<String,List<T>> hgetAllList(String key,Class<T> clazz);
	
	/**
	 * 
	 * @Title: hgetAllList   
	 * @Description: 获取key下的field value集合。
	 * @param index
	 * @param key
	 * @param clazz
	 * @return
	 * @return Map<String,List<T>>
	 */
	<T> Map<String,List<T>> hgetAllList(int index,String key,Class<T> clazz);
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
	
	/**
	 * 
	 * @Title: hvals   
	 * @Description: 获取key下的所有value
	 * @param key
	 * @param clazz
	 * @return
	 * @return List<T>
	 */
	<T> List<T> hvals(String key,Class<T> clazz);
}
