package com.petecat.interchan.redis.commands.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IRedisHashExtCommands
 * @Description:Redis hash扩展类
 * @author: mhuang
 * @date: 2018年5月9日 下午10:09:37
 */
public interface IRedisHashExtCommands {

    /**
     * @param key
     * @param field
     * @param value
     * @param seconds
     * @return boolean
     * @Title: hset
     * @Description: 设置hash过期时间
     */
    boolean hset(String key, String field, Object value, long seconds);

    /**
     * @param key
     * @param field
     * @param clazz
     * @return T
     * @Title: hget
     * @Description: 获取值转相应类型（该方法只支持单个）
     */
    <T> T hget(String key, String field, Class<T> clazz);

    /**
     * @param key
     * @param field
     * @param clazz
     * @return T
     * @Title: hget
     * @Description: 获取值转相应类型（该方法只支持单个）
     */
    <T> T hget(int dbIndex, String key, String field, Class<T> clazz);

    /**
     * @param dbIndex
     * @param key
     * @param fields
     * @param clazz
     * @return List<T>
     * @Title: hmget
     * @Description: 获取多个field的值
     */
    <T> List<T> hmget(String key, Collection<String> fields, Class<T> clazz);

    /**
     * @param dbIndex
     * @param key
     * @param fields
     * @param clazz
     * @return List<T>
     * @Title: hmget
     * @Description: 获取多个field的值
     */
    <T> List<T> hmget(int dbIndex, String key, Collection<String> fields, Class<T> clazz);

    /**
     * @param key
     * @param field
     * @param clazz
     * @return List<T>
     * @Title: hgetList
     * @Description: 获取值转相应类型（该方法只支持单个）
     */
    <T> List<T> hgetList(String key, String field, Class<T> clazz);

    /**
     * @param dbIndex
     * @param key
     * @param field
     * @param clazz
     * @return List<T>
     * @Title: hgetList
     * @Description: 获取值转相应类型（该方法只支持单个）
     */
    <T> List<T> hgetList(int dbIndex, String key, String field, Class<T> clazz);

    /**
     * @param key
     * @param clazz
     * @return Map<String                               ,                               T>
     * @Title: hgetAll
     * @Description: 获取key下的field value集合。
     */
    <T> Map<String, T> hgetAll(String key, Class<T> clazz);

    /**
     * @param index
     * @param key
     * @param clazz
     * @return Map<String                               ,                               T>
     * @Title: hgetAll
     * @Description: 获取key下的field value集合。
     */
    <T> Map<String, T> hgetAll(int index, String key, Class<T> clazz);

    /**
     * @param key
     * @param clazz
     * @return Map<String                               ,                               List                               <                               T>>
     * @Title: hgetAllList
     * @Description: 获取key下的field value集合。
     */
    <T> Map<String, List<T>> hgetAllList(String key, Class<T> clazz);

    /**
     * @param index
     * @param key
     * @param clazz
     * @return Map<String                               ,                               List                               <                               T>>
     * @Title: hgetAllList
     * @Description: 获取key下的field value集合。
     */
    <T> Map<String, List<T>> hgetAllList(int index, String key, Class<T> clazz);

    /**
     * @param index
     * @param key
     * @return List<String>
     * @Title: hvals
     * @Description: 获取key所有的值
     */
    <T> List<T> hvals(int index, String key, Class<T> clazz);

    /**
     * @param key
     * @param clazz
     * @return List<T>
     * @Title: hvals
     * @Description: 获取key下的所有value
     */
    <T> List<T> hvals(String key, Class<T> clazz);
}
