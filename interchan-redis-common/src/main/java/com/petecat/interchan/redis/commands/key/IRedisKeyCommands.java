package com.petecat.interchan.redis.commands.key;

/**
 * @ClassName: IRedisKeyCommands
 * @Description:设置过期时间
 * @author: mhuang
 * @date: 2017年9月1日 上午9:16:36
 */
public interface IRedisKeyCommands {

    /**
     * @param key
     * @param seconds
     * @return boolean
     * @Title: expireat
     * @Description: 设置过期时间（秒）
     */
    Boolean expire(String key, long seconds);

    /**
     * @param index
     * @param key
     * @param seconds
     * @return boolean
     * @Title: expireat
     * @Description: 设置过期时间
     */
    Boolean expire(int index, String key, long seconds);

    /**
     * @param key
     * @return Boolean
     * @Title: exists
     * @Description: 判断key是否存在
     */
    Boolean exists(String key);

    /**
     * @param key
     * @return Boolean
     * @Title: exists
     * @Description: 判断key是否存在
     */
    Boolean exists(int index, String key);
}
