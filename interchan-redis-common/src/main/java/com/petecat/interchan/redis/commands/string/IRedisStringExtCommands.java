package com.petecat.interchan.redis.commands.string;


/**
 * @ClassName: IRedisStringExtCommands
 * @Description:redis string扩展工具
 * @author: mhuang
 * @date: 2018年5月9日 下午10:03:12
 */
public interface IRedisStringExtCommands {

    /**
     * @param key
     * @param clazz
     * @return T
     * @Title: get
     * @Description: 获取
     */
    <T> T get(String key, Class<T> clazz);

    /**
     * @param dbIndex
     * @param key
     * @param clazz
     * @return T
     * @Title: get
     * @Description: 获取
     */
    <T> T get(int dbIndex, String key, Class<T> clazz);
}
