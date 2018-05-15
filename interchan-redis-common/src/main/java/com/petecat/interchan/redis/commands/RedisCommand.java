/**  
 * All rights Reserved, Designed By http://www.pete-cat.com/
 * TODO(用一句话描述该文件做什么)   
 * @Title:  RedisCommand.java   
 * @Package com.petecat.interchan.redis.commands  
 * @author: 成都皮特猫科技     
 * @date:2018年5月15日 下午2:14:25   
 * @version V1.0 
 * @Copyright: 2018 www.pete-cat.com Inc. All rights reserved. 
 * 注意：本内容仅限于成都皮特猫信息技术有限公司内部传阅，禁止外泄以及用于其他的商业目
 */  
package com.petecat.interchan.redis.commands;

import org.springframework.data.redis.core.RedisTemplate;

/** 
 * redis命令
 * @ClassName:  RedisCommand   
 * @author: admin
 * @date:   2018年5月15日 下午2:14:25   
 */
public interface RedisCommand<T> {
	
  public T executeCommand(RedisTemplate template);
  
}
