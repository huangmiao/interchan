package com.petecat.interchan.jackson.common;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;
import java.util.TimeZone;

/**
 * 
 * @ClassName:  JacksonObjectMappper   
 * @Description:jacksonObjectMapper
 * @author: mhuang
 * @date:   2018年5月18日 下午2:55:42
 */
public class JacksonObjectMappper  extends ObjectMapper
{
	private static final long serialVersionUID = 1L;

	public JacksonObjectMappper(){
		findAndRegisterModules();
		configOverride(Map.class);
		setSerializationInclusion(Include.NON_NULL); //序列化忽略null值数据
		configure(MapperFeature.INFER_PROPERTY_MUTATORS, false);//
		configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		setTimeZone(TimeZone.getTimeZone("GMT+8")); //springboot架构默时区少8小时。补偿。
		configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
		configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true);
	}
}
