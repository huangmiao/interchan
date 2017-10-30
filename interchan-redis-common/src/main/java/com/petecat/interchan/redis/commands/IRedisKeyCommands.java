package com.petecat.interchan.redis.commands;

/**
 * 
 * @ClassName:  IRedisKeyCommands   
 * @Description:设置过期时间 
 * @author: mhuang
 * @date:   2017年9月1日 上午9:16:36
 */
public interface IRedisKeyCommands {

	/**
	 * 
	 * @Title: expireat   
	 * @Description: 设置过期时间（秒）
	 * @param key
	 * @param seconds
	 * @return
	 * @return boolean
	 */
	boolean expire(String key,long seconds);
	
	/**
	 * 
	 * @Title: expireat   
	 * @Description: 设置过期时间
	 * @param index
	 * @param key
	 * @param seconds
	 * @return
	 * @return boolean
	 */
	boolean expire(int index,String key,long seconds);
	
	
}
