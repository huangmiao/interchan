package com.petecat.interchan.redis.commands.sets.sorted;

import java.util.List;

/**
 * 
 * @ClassName:  IRedisSortedSetCommands   
 * @Description:有序集合
 * @author: mhuang
 * @date:   2017年8月31日 上午11:43:50
 */
public interface IRedisSortedSetCommands {

	/**
	 * 
	 * @Title: zadd   
	 * @Description: 添加
	 * @param key	
	 * 		简
	 * @param score
	 * 		分数
	 * @param value
	 * 		值
	 * @return
	 * @return boolean
	 */
	boolean zadd(String key,double score,Object value);
	
	///////////////////////////操作其他库/////////////////////
	/**
	 * 
	 * @Title: zadd   
	 * @Description: 添加
	 * @param key	
	 * 		简
	 * @param score
	 * 		分数
	 * @param value
	 * 		值
	 * @return
	 * @return boolean
	 */
	boolean zadd(int index,String key,double score,Object value);
	
	
	/** 
	 *   
	 * @Title: zincrby
	 * @param index
	 * @param key
	 * @param score
	 * @param member
	 * @return double     
	 */
	double zIncrBy(int index, String key, double score, Object member);

	/** 
	 * 最大的分数在前获取
	 * @Title: zRevRange
	 * @param index
	 * @param key
	 * @param start
	 * @param end
	 * @param clz
	 * @return List<T>     
	 */
	<T>List<T> zRevRange(int index, String key, long start, long end, Class<T> clz);
	
	
	
}
