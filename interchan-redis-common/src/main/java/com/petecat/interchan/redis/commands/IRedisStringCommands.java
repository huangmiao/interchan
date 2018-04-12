package com.petecat.interchan.redis.commands;

import java.util.Collection;
import java.util.Map;

/**
 * 
 * @ClassName:  IRedisStringRepository   
 * @Description:String工具类 
 * @author: mhuang
 * @date:   2017年8月31日 上午10:41:52
 */
public interface IRedisStringCommands {

	/**
	 * 
	 * @Title: set   
	 * @Description: 设置key，value
	 * @param key
	 * @param value
	 * @return
	 * @return boolean
	 */
	boolean set(String key, Object value); 
	
	/**
	 * 
	 * @Title: set   
	 * @Description: 设置过期时间
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 * @return boolean
	 */
	boolean set(String key,Object value,long expireTime);
	
	/**
	 * 
	 * @Title: get   
	 * @Description:获取
	 * @param key
	 * @return
	 * @return String
	 */
	String get(String key);
	
	/**
	 * 
	 * @Title: get   
	 * @Description: 
	 * @param index
	 * @param key
	 * @return
	 * @return String
	 */
	String get(int index,String key);
	
	/**
	 * 
	 * @Title: incr   
	 * @Description: 原子追加
	 * @param key
	 * @return
	 * @return Long
	 */
	Long incr(String key);
	
	/**
	 * 
	 * @Title: incr   
	 * @Description: 原子追加
	 * @param index
	 * @param key
	 * @return
	 * @return Long
	 */
	Long incr(int index,String key);
	/**
	 * 
	 * @Title: mset   
	 * @Description: 设置多个值
	 * @param map
	 * @return
	 * @return boolean
	 */
	boolean mset(Map<String, Object> map);
	
	/**
	 * 
	 * @Title: mget   
	 * @Description: 获取多个值  
	 * @param keys
	 * @return
	 * @return Collection<Object>
	 */
	Collection<String> mget(Collection<String> keys);
	/**
	 * 
	 * @Title: append   
	 * @Description: 追加
	 * @param key
	 * @param value
	 * @return
	 * @return long
	 */
	long append(String key,Object value);
	
	/**
	 * 
	 * @Title: del   
	 * @Description: 删除key   
	 * @param key
	 * @return
	 * @return long
	 */
	public long del(String key);
	
	////////////////////////操作其他库//////////////////////////////
	/**
	 * 
	 * @Title: set   
	 * @Description: 设置key，value
	 * @param key
	 * @param value
	 * @return
	 * @return boolean
	 */
	boolean set(int index,String key, Object value); 
	
	/**
	 * 
	 * @Title: set   
	 * @Description: 设置过期时间
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 * @return boolean
	 */
	boolean set(int index,String key,Object value,long expireTime);
	
	/**
	 * 
	 * @Title: mset   
	 * @Description: 设置多个值
	 * @param map
	 * @return
	 * @return boolean
	 */
	boolean mset(int index,Map<String, Object> map);
	
	/**
	 * 
	 * @Title: mget   
	 * @Description: 获取多个值  
	 * @param keys
	 * @return
	 * @return Collection<Object>
	 */
	Collection<String> mget(int index,Collection<String> keys);
	/**
	 * 
	 * @Title: append   
	 * @Description: 追加
	 * @param key
	 * @param value
	 * @return
	 * @return long
	 */
	long append(int index,String key,Object value);
	
	/**
	 * 
	 * @Title: del   
	 * @Description: 删除key   
	 * @param key
	 * @return
	 * @return long
	 */
	public long del(int index,String key);
}
