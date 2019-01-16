package com.petecat.interchan.redis.commands.sets.sorted;

import java.util.List;

/**
 * @ClassName: IRedisSortedSetCommands
 * @Description:有序集合
 * @author: mhuang
 * @date: 2017年8月31日 上午11:43:50
 */
public interface IRedisSortedSetCommands {

    /**
     * @param key   简
     * @param score 分数
     * @param value 值
     * @return boolean
     * @Title: zadd
     * @Description: 添加
     */
    boolean zadd(String key, double score, Object value);

    Long zadd(String key, List<RedisSortedSetDTO> list);


    ///////////////////////////操作其他库/////////////////////

    /**
     * @param key   简
     * @param score 分数
     * @param value 值
     * @return boolean
     * @Title: zadd
     * @Description: 添加
     */
    boolean zadd(int index, String key, double score, Object value);


    /**
     * @param index
     * @param key
     * @param score
     * @param member
     * @return double
     * @Title: zincrby
     */
    double zIncrBy(int index, String key, double score, Object member);

    /**
     * 最大的分数在前获取
     *
     * @param index
     * @param key
     * @param start
     * @param end
     * @param clz
     * @return List<T>
     * @Title: zRevRange
     */
    <T> List<T> zRevRange(int index, String key, long start, long end, Class<T> clz);

    Long zadd(int index, String key, List<RedisSortedSetDTO> list);

}
