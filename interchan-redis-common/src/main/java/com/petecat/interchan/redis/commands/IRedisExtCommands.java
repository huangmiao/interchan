package com.petecat.interchan.redis.commands;

import com.petecat.interchan.redis.commands.hash.IRedisHashCommands;
import com.petecat.interchan.redis.commands.hash.IRedisHashExtCommands;
import com.petecat.interchan.redis.commands.key.IRedisKeyCommands;
import com.petecat.interchan.redis.commands.list.IRedisListCommands;
import com.petecat.interchan.redis.commands.sets.sorted.IRedisSortedSetCommands;
import com.petecat.interchan.redis.commands.string.IRedisStringCommands;
import com.petecat.interchan.redis.commands.string.IRedisStringExtCommands;

/**
 * @ClassName: IRedisCommands
 * @Description:通用Redis接口
 * @author: mhuang
 * @date: 2017年8月31日 下午1:46:51
 */
public interface IRedisExtCommands extends
        IRedisStringCommands, IRedisStringExtCommands,
        IRedisHashCommands, IRedisHashExtCommands,
        IRedisListCommands,
        IRedisSortedSetCommands,
        IRedisKeyCommands {
    <T> T executeRedisCommand(RedisCommand<T> redisCommand);
}
