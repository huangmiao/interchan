package com.petecat.interchan.logger.common;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.elasticsearch.search.DocValueFormat.DateTime;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mhuang.common.util.DateTimeUtils;
import com.mhuang.common.util.IpUtils;
import com.petecat.interchan.logger.common.annotation.LogLogger;
import com.petecat.interchan.logger.common.model.EsOperatorLogger;

/**
 * 
 * @ClassName:  LoggerUtils   
 * @Description:日志工具类
 * @author: mhuang
 * @date:   2017年8月3日 下午2:53:10
 */
public class LoggerUtils {

	/**
	 * @Title: getEsLogger   
	 * @Description: 组装EsLogger数据
	 * @param request
	 * @return
	 * @return EsOperatorLogger
	 */
	public static EsOperatorLogger getEsLogger(HttpServletRequest request,JoinPoint jPoint){
		EsOperatorLogger esLogger = new EsOperatorLogger();
		
		LocalDateTime now = LocalDateTime.now();
		//基础配置
		esLogger.setIp(IpUtils.getIp(request));
		esLogger.setType(request.getMethod());
		esLogger.setUrl(request.getRequestURL().toString());
		esLogger.setStartDate(DateTimeUtils.localDateTimeToDate(now));
		esLogger.setStartDateFormatter(now.toString()+"+08:00");
		//auth
		packAuthData(request,esLogger);
		
		Object[] args = jPoint.getArgs();
		//组装传输对象数组
		if(args != null && args.length>0){
			String data = JSON.toJSONString(args);
			data = StringEscapeUtils.unescapeJson(data);
			esLogger.setSendData(data);
		}
		
		esLogger.setQueryData(request.getQueryString());
		
		String clazz = jPoint.getTarget().getClass().getName();
		esLogger.setClazz(clazz);
		
		MethodSignature method = (MethodSignature)jPoint.getSignature();   
		esLogger.setMethod(method.getName());
		
		//注解
		try {
			String remark = getMethodByRemark(method);
			esLogger.setRemark(remark);
		} catch (Exception e) {}
		return esLogger;
	}
	
	public static EsOperatorLogger getEsLogger(JoinPoint jPoint){
		EsOperatorLogger esLogger = new EsOperatorLogger();
		LocalDateTime now = LocalDateTime.now();
		//基础配置
		esLogger.setStartDate(DateTimeUtils.localDateTimeToDate(now));
		esLogger.setStartDateFormatter(now.toString()+"+08:00");
		//auth
		Object[] args = jPoint.getArgs();
		//组装传输对象数组
		if(args != null && args.length>0){
			String data;
			try{
				data = JSON.toJSONString(args);
				data = StringEscapeUtils.unescapeJson(data);
				esLogger.setSendData(data);
			}catch(Exception e){
			}
		}
		
		String clazz = jPoint.getTarget().getClass().getName();
		esLogger.setClazz(clazz);
		
		MethodSignature method = (MethodSignature)jPoint.getSignature();   
		esLogger.setMethod(method.getName());
		
		//注解
		try {
			String remark = getMethodByRemark(method);
			esLogger.setRemark(remark);
		} catch (Exception e) {}
		return esLogger;
	}
	
	public static void packAuthData(HttpServletRequest request,EsOperatorLogger esLogger){
		if(LoggerGlobal.getJwtHeader()){
			esLogger.setHeaderData(
				StringEscapeUtils.unescapeJson(
					request.getHeader(LoggerGlobal.getJwtHeaderName())
				)
			);
			if(StringUtils.isNotBlank(esLogger.getHeaderData())){
				JSONObject obj  = JSON.parseObject(esLogger.getHeaderData());
				esLogger.setUserId(obj.getString("userId"));
			}
		}
	} 
	/**  
     * 获取注解中对方法的描述信息 用于Controller层注解  
     *  
     * @param joinPoint 切点  
     * @return 方法描述  
     * @throws Exception  
     */    
     public  static String getMethodByRemark(MethodSignature methodSignature)  throws Exception {    
        Method method0 =  methodSignature.getMethod();
        LogLogger logLogger = method0.getAnnotation(LogLogger.class);
        if(logLogger != null){
    		return logLogger.remark();
    	}
         return null;    
    }    
}
