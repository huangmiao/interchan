package com.petecat.interchan.redis.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;


/**
 * 
 * @ClassName:  RedisCommands   
 * @Description:Redis 操作类
 * @author: mhuang
 * @date:   2017年8月31日 下午3:57:55
 */
@Component("redisCommands")
public class RedisExtCommands extends AbsRedisCommands{

	@Autowired
	public void setRedisTemplate(RedisTemplate<String, ?> redisTemplate) {
		this.baseTempalte = redisTemplate;
	}

	@Override
	public boolean hset(String key, String field, Object value, long seconds) {
		boolean result = hset(key, field, value);
		if(result){
			result = expire(key, seconds);
		}
		return result;
	}


	@Override
	public <T> T hget(String key, String field, Class<T> clazz) {
		return hget(defaultDbIndex,key, field,clazz);
	}

	@Override
	public <T> T hget(int dbIndex, String key, String field, Class<T> clazz) {
		String value = hget(dbIndex,key, field);
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return JSON.parseObject(value, clazz);
	}
	
	@Override
	public <T> List<T> hgetList(String key, String field, Class<T> clazz) {
	    return hgetList(defaultDbIndex,key, field,clazz);
	}

	@Override
    public <T> List<T> hgetList(int dbIndex,String key, String field, Class<T> clazz) {
        String value = hget(dbIndex,key, field);
        if(StringUtils.isEmpty(value)){
            return null;
        }
        return JSON.parseArray(value, clazz);
    }
	@Override
	public <T> List<T> hvals(int index, String key, Class<T> clazz) {
		List<String> value = hvals(index, key);
		if(CollectionUtils.isEmpty(value)){
			return null;
		}
		return value.parallelStream().map(
			val -> JSON.parseObject(val,clazz)
		).collect(Collectors.toList());
//		return JSON.parseArray(JSON.toJSONString(value), clazz);
	}

	@Override
	public <T> Map<String, T> hgetAll(int index, String key, Class<T> clazz) {
		Map<String, String> map = hgetall(index, key);
		Map<String, T> result = new HashMap<>(map.size());
		map.forEach((mapKey,mapValue)->{
			result.put(mapKey, JSON.parseObject(mapValue,clazz));
		});
		return result;
	}
	
	@Override
	public <T> T get(String key, Class<T> clazz) {
		String value = get(key);
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return JSON.parseObject(value, clazz);
	}

	@Override
	public <T> T get(int dbIndex, String key, Class<T> clazz) {
		String value = get(dbIndex,key);
		if(StringUtils.isEmpty(value)){
			return null;
		}
		return JSON.parseObject(value, clazz);
	}

	@Override
	public <T> Map<String, T> hgetAll(String key, Class<T> clazz) {
		Map<String,String> map = hgetall(key);
		Map<String, T> result = new HashMap<>(map.size());
		map.forEach((mapKey,mapValue)->{
			result.put(mapKey, JSON.parseObject(mapValue,clazz));
		});
		return result;
	}

	@Override
	public <T> Map<String, List<T>> hgetAllList(String key, Class<T> clazz) {
		Map<String,String> map = hgetall(key);
		Map<String, List<T>> result = new HashMap<>(map.size());
		map.forEach((mapKey,mapValue)->{
			result.put(mapKey, JSON.parseArray(mapValue,clazz));
		});
		return result;
	}

	@Override
	public <T> Map<String, List<T>> hgetAllList(int index, String key, Class<T> clazz) {
		Map<String,String> map = hgetall(index,key);
		Map<String, List<T>> result = new HashMap<>(map.size());
		map.forEach((mapKey,mapValue)->{
			result.put(mapKey, JSON.parseArray(mapValue,clazz));
		});
		return result;
	}
	
	@Override
	public <T> List<T> hvals(String key, Class<T> clazz) {
		List<String> value = hvals(key);
		if(CollectionUtils.isEmpty(value)){
			return null;
		}
		return value.parallelStream().map(
			val -> JSON.parseObject(val,clazz)
		).collect(Collectors.toList());
	}

	
}
