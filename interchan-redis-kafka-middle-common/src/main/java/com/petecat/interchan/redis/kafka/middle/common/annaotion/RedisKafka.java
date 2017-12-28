package com.petecat.interchan.redis.kafka.middle.common.annaotion;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/**
 * 
 * @ClassName:  LogLogger   
 * @Description:日志
 * @author: mhuang
 * @date:   2017年8月15日 下午12:29:50
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface RedisKafka {

	/**
	 * 
	 * @Title: NotRepeat   
	 * @Description: 示范不能重复 
	 * @return 默认不能重复
	 * @return boolean
	 */
	boolean NotRepeat() default true; 
}
