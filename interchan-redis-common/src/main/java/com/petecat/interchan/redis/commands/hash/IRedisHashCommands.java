package com.petecat.interchan.redis.commands.hash;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: IRedisHashCommands
 * @Description:Redis hash操作
 * @author: mhuang
 * @date: 2017年8月31日 上午11:48:42
 */
public interface IRedisHashCommands {

    /**
     * @param key
     * @param field
     * @param value
     * @return boolean
     * @Title: hset
     * @Description: 设置单个值
     */
    boolean hset(String key, String field, Object value);

    /**
     * @param key
     * @param field
     * @return String
     * @Title: hget
     * @Description: 获取单个对key的字段的值
     */
    String hget(String key, String field);

    /**
     * @param key    键
     * @param params 多个值和字段（Map格式）
     * @return boolean
     * @Title: hmset
     * @Description: 根据键设置多个字段和值
     */
    boolean hmset(String key, Map<String, Object> params);

    /**
     * @param key    获取的键
     * @param fields 获取的字段列表
     * @return Collection<String>
     * @Title: hmget
     * @Description: 根获取这个key中选择的多个field值
     */
    Collection<String> hmget(String key, Collection<String> fields);

    /**
     * @param key 建
     * @return Map<String               ,               String> 字段和值
     * @Title: hgetall
     * @Description: 获取key的所有的字段、值
     */
    Map<String, String> hgetall(String key);

    ///////////////////////////操作其他库////////////////////////////

    /**
     * @param key
     * @param field
     * @param value
     * @return boolean
     * @Title: hset
     * @Description: 设置单个值
     */
    boolean hset(int index, String key, String field, Object value);


    /**
     * @param key
     * @param field
     * @return String
     * @Title: hget
     * @Description: 获取单个对key的字段的值
     */
    String hget(int index, String key, String field);

    /**
     * @param key    键
     * @param params 多个值和字段（Map格式）
     * @return boolean
     * @Title: hmset
     * @Description: 根据键设置多个字段和值
     */
    boolean hmset(int index, String key, Map<String, Object> params);

    boolean hmsetList(String key, Map<String, List<Object>> params);

    boolean hmsetList(int index, String key, Map<String, List<Object>> params);

    /**
     * @param key    获取的键
     * @param fields 获取的字段列表
     * @return Collection<String>
     * @Title: hmget
     * @Description: 根获取这个key中选择的多个field值
     */
    Collection<String> hmget(int index, String key, Collection<String> fields);

    /**
     * @param key 建
     * @return Map<String               ,               String> 字段和值
     * @Title: hgetall
     * @Description: 获取key的所有的字段、值
     */
    Map<String, String> hgetall(int index, String key);

    List<String> hvals(String key);

    /**
     * @param index
     * @param key
     * @return List<String>
     * @Title: hvals
     * @Description: 获取key所有的值
     */
    List<String> hvals(int index, String key);

    Long hdel(int index, String key, Object field);

    Long hdel(String key, Object field);

    Long hincrby(String key, String field, Long incroment);

    Long hincrby(int index, String key, String field, Long incroment);

    List<String> hkeys(String key);

    List<String> hkeys(int index, String key);

}
