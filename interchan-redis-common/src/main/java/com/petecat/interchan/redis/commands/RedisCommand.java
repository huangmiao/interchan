
package com.petecat.interchan.redis.commands;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis命令
 *
 * @ClassName: RedisCommand
 * @author: admin
 * @date: 2018年5月15日 下午2:14:25
 */
public interface RedisCommand<T> {

    T executeCommand(RedisTemplate<?, ?> template);

}
