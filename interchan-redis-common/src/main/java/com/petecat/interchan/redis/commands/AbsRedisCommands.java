package com.petecat.interchan.redis.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import lombok.Setter;

/**
 * 
 * @ClassName:  AbsRedisCommands   
 * @Description:通用抽象通用处理
 * @author: mhuang
 * @date:   2017年8月31日 下午2:02:27
 */
public abstract class AbsRedisCommands implements IRedisExtCommands{

	@Setter
	RedisTemplate<String, ?> baseTempalte;

	@Value("${spring.redis.database}")
	int defaultDbIndex;
	
	@Override
	public boolean hset(String key, String field, Object value) {
		return hset(defaultDbIndex, key, field, value);
	}

	@Override
	public boolean hmset(String key, Map<String, Object> params) {
		return hmset(defaultDbIndex, key, params);
	}

	@Override
	public Collection<String> hmget(String key, Collection<String> fields) {
		return hmget(defaultDbIndex, key, fields);
	}

	@Override
	public Map<String, String> hgetall(String key) {
		return hgetall(defaultDbIndex,key);
	}

	@Override
	public boolean zadd(String key, double score, Object value) {
		return zadd(defaultDbIndex, key, score, value);
	}

	@Override
	public boolean set(String key, Object value) {
		return set(defaultDbIndex, key, value);
	}

	@Override
	public boolean set(String key, Object value, long expireTime) {
		return set(defaultDbIndex,key,value,expireTime);
	}

	@Override
	public Long incr(String key) {
		return incr(defaultDbIndex,key);
	}
	@Override
	public String get(String key) {
		return get(defaultDbIndex,key);
	}
	
	@Override
	public boolean mset(Map<String, Object> map) {
		return mset(defaultDbIndex,map);
	}

	@Override
	public Collection<String> mget(Collection<String> keys) {
		return mget(defaultDbIndex, keys);
	}

	@Override
	public long del(String key) {
		return del(defaultDbIndex, key);
	}

	@Override
	public long append(int index, String key, Object value) {
		return 0L;
	}
	
	/**
	 * 
	 * @Title: zadd   
	 * @Description: 添加
	 * @param key	
	 * 		简
	 * @param score
	 * 		分数
	 * @param value
	 * 		值
	 * @return
	 * @return boolean
	 */
	@Override
	public boolean zadd(int index,String key, double score, Object value) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			String val = JSON.toJSONString(value);
			return connection.zAdd(serializer.serialize(key), score, serializer.serialize(val));
		});
	}

	@Override
	public boolean hset(int index,String key, String field, Object value) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			String v = value instanceof String ? (String) value : JSON.toJSONString(value);
			return connection.hSet(serializer.serialize(key), serializer.serialize(field), serializer.serialize(v));
		});
	}

	@Override
	public boolean hmset(int index,String key, Map<String, Object> params) {
		if(CollectionUtils.isEmpty(params))
			return false;
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			Map<byte[], byte[]> hmsetMap = new HashMap<>(params.size());
			params.forEach((field,value)->{
    			String val = JSON.toJSONString(value);
    			hmsetMap.put(
    					serializer.serialize(field),
    					serializer.serialize(val)
				);
    		});
			connection.hMSet(serializer.serialize(key), hmsetMap);
			return true;
		});
	}

	@Override
	public Collection<String> hmget(int index,String key, Collection<String> fields) {
		if(CollectionUtils.isEmpty(fields))
			return Collections.emptyList();
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
    		
    		List<byte[]> collector = fields.parallelStream().map(
				value -> serializer.serialize(value)
			).collect(Collectors.toList());
    		
    		List<byte[]> valueList = connection.hMGet(serializer.serialize(key),(byte[][]) collector.toArray());
    		
    		return valueList.parallelStream().map(
    			value-> serializer.deserialize(value)
			).collect(Collectors.toList());
		});
	}

	@Override
	public Map<String, String> hgetall(int index,String key) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
        	Map<byte[],byte[]> datas = connection.hGetAll(serializer.serialize(key));
        	Map<String, String> result = new HashMap<>(datas.size());
	        datas.forEach((field,value)->{
	        	result.put(
	        			serializer.deserialize(field), 
	        			serializer.deserialize(value)
    			);
	        });
            return result;
  		});
	}
	
	@Override
	public List<String> hvals(String key) {
		return hvals(defaultDbIndex, key);
	}
	
	@Override
	public List<String> hkeys(String key) {
		return hkeys(defaultDbIndex,key);
	}
	@Override
	public List<String> hkeys(int index, String key) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
    		
    		Set<byte[]> valueList = connection.hKeys(serializer.serialize(key));
    		
    		return valueList.parallelStream().map(
    			value-> serializer.deserialize(value)
			).collect(Collectors.toList());
		});
	}
	@Override
	public List<String> hvals(int index, String key) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
        	List<byte[]> datas = connection.hVals(serializer.serialize(key));
        	return datas.parallelStream().map(value -> serializer.deserialize(value)).collect(Collectors.toList()); 
  		});
	}

	@Override
	public boolean set(int index,String key, Object value) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			connection.set(serializer.serialize(key), serializer.serialize(JSON.toJSONString(value)));
			return true;
		});
	}

	@Override
	public Long incr(int index, String key) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			return connection.incr(serializer.serialize(key));
		});
	}
	@Override
	public boolean set(int index,String key, Object value, long expireTime) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			connection.setEx(serializer.serialize(key), expireTime, serializer.serialize(JSON.toJSONString(value)));
			return true;
		});
	}

	@Override
	public String get(int index,String key) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			byte[] value = connection.get(serializer.serialize(key));
			return serializer.deserialize(value);
		});
	}
	
	@Override
	public boolean mset(int index,Map<String, Object> map) {
		if(CollectionUtils.isEmpty(map))
			return false;
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			Map<byte[], byte[]> result = new HashMap<>(map.size());
			map.forEach((key,value)->{
	        	result.put(
        			serializer.serialize(key), 
        			serializer.serialize(JSON.toJSONString(value))
    			);
	        });
	        
			connection.mSet(result);
			return true;
		});
	}

	@Override
	public Collection<String> mget(int index,Collection<String> keys) {
		if(CollectionUtils.isEmpty(keys))
			return Collections.emptyList();
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			
			List<byte[]> collector = keys.parallelStream().map(value -> serializer.serialize(value)).collect(Collectors.toList());
			collector = connection.mGet((byte[][])collector.toArray());
			
			return collector.parallelStream().map(
	    			value-> serializer.deserialize(value)
				).collect(Collectors.toList());
		});
	}

	@Override
	public long append(String key, Object value) {
		return 0;
	}

	@Override
	public long del(int index,String key) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			return connection.del(serializer.serialize(key));
		});
	}
	
	@Override
	public String hget(String key, String field) {
		return hget(defaultDbIndex, key, field);
	}


	@Override
	public String hget(int index, String key, String field) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			byte[] hgerResult = connection.hGet(serializer.serialize(key), serializer.serialize(field));
			return serializer.deserialize(hgerResult);
		});
	}
	
	/**
	 * 
	 * @Title: expireat   
	 * @Description: 设置过期时间（
	 * @param index
	 * @param key
	 * @param secords
	 * @return
	 * @return int
	 */
	@Override
	public boolean expire(String key,long seconds){
		return expire(defaultDbIndex, key, seconds);
	}
	
	/**
	 * 
	 * @Title: expireat   
	 * @Description: 设置过期时间
	 * @param index
	 * @param key
	 * @param seconds
	 * @return
	 * @return int
	 */
	@Override
	public boolean expire(int index,String key,long seconds){
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			return connection.expire(serializer.serialize(key), seconds);
		});
	}
	
	@Override
	public Long hincrby(String key, String field, Long incroment) {
		return hincrby(defaultDbIndex,key,field,incroment);
	}
	
	@Override
	public Long hincrby(int index, String key, String field, Long incroment) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			return connection.hIncrBy(serializer.serialize(key), serializer.serialize(field), incroment);
		});
	}
	@Override
	public Long hdel(int index, String key, Object field) {
		return baseTempalte.execute((RedisConnection connection)->{
			connection.select(index);
			RedisSerializer<String> serializer = baseTempalte.getStringSerializer();
			if(field instanceof String){
				String f = (String) field;
				return connection.hDel(serializer.serialize(key), serializer.serialize(f));
			}else{
				@SuppressWarnings("unchecked")
				Collection<Object> val = (Collection<Object>)field;
				byte[][] f = (byte[][]) val.parallelStream().map(
					value-> serializer.serialize((String) value)
				).toArray();
				return connection.hDel(serializer.serialize(key), f);
			}
		});
	}

	@Override
	public Long hdel(String key, Object field) {
		return hdel(defaultDbIndex,key,field);
	}
}
