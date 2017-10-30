package com.petecat.interchan.redis.lock;

import lombok.Data;

/**
 * 
 * @ClassName:  Lock   
 * @Description:锁
 * @author: mhuang
 * @date:   2017年7月12日 上午10:50:43
 */
@Data
public class Lock {
	private String name;
    private String value;
}
