package com.petecat.interchan.redis.commands.string;

import java.util.Collection;
import java.util.Map;

/**
 * @ClassName: IRedisStringRepository
 * @Description:String工具类
 * @author: mhuang
 * @date: 2017年8月31日 上午10:41:52
 */
public interface IRedisStringCommands {

    /**
     * @param key
     * @param value
     * @return boolean
     * @Title: set
     * @Description: 设置key，value
     */
    boolean set(String key, Object value);

    /**
     * @param key
     * @param value
     * @param expireTime
     * @return boolean
     * @Title: set
     * @Description: 设置过期时间
     */
    boolean set(String key, Object value, long expireTime);

    /**
     * @param key
     * @return String
     * @Title: get
     * @Description:获取
     */
    String get(String key);

    /**
     * @param index
     * @param key
     * @return String
     * @Title: get
     * @Description:
     */
    String get(int index, String key);

    /**
     * @param key
     * @return Long
     * @Title: incr
     * @Description: 原子追加
     */
    Long incr(String key);

    /**
     * @param index
     * @param key
     * @return Long
     * @Title: incr
     * @Description: 原子追加
     */
    Long incr(int index, String key);

    /**
     * @param map
     * @return boolean
     * @Title: mset
     * @Description: 设置多个值
     */
    boolean mset(Map<String, Object> map);

    /**
     * @param keys
     * @return Collection<Object>
     * @Title: mget
     * @Description: 获取多个值
     */
    Collection<String> mget(Collection<String> keys);

    /**
     * @param key
     * @param value
     * @return long
     * @Title: append
     * @Description: 追加
     */
    long append(String key, Object value);

    /**
     * @param key
     * @return long
     * @Title: del
     * @Description: 删除key
     */
    public long del(String key);

    ////////////////////////操作其他库//////////////////////////////

    /**
     * @param key
     * @param value
     * @return boolean
     * @Title: set
     * @Description: 设置key，value
     */
    boolean set(int index, String key, Object value);

    /**
     * @param key
     * @param value
     * @param expireTime
     * @return boolean
     * @Title: set
     * @Description: 设置过期时间
     */
    boolean set(int index, String key, Object value, long expireTime);

    /**
     * @param map
     * @return boolean
     * @Title: mset
     * @Description: 设置多个值
     */
    boolean mset(int index, Map<String, Object> map);

    /**
     * @param keys
     * @return Collection<Object>
     * @Title: mget
     * @Description: 获取多个值
     */
    Collection<String> mget(int index, Collection<String> keys);

    /**
     * @param key
     * @param value
     * @return long
     * @Title: append
     * @Description: 追加
     */
    long append(int index, String key, Object value);

    /**
     * @param key
     * @return long
     * @Title: del
     * @Description: 删除key
     */
    public long del(int index, String key);
}
