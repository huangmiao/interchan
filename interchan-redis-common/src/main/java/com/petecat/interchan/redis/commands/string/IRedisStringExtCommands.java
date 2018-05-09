package com.petecat.interchan.redis.commands.string;


/**
 * 
 * @ClassName:  IRedisStringExtCommands   
 * @Description:redis string扩展工具
 * @author: mhuang
 * @date:   2018年5月9日 下午10:03:12
 */
public interface IRedisStringExtCommands {
	
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
}
