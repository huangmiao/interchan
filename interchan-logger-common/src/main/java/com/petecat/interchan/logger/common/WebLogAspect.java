package com.petecat.interchan.logger.common;

import java.lang.reflect.Proxy;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.mhuang.common.util.DateTimeUtils;
import com.petecat.interchan.logger.common.dao.EsLoggerDao;
import com.petecat.interchan.logger.common.model.EsOperatorLogger;

/**
 * 
 * @ClassName:  LogAspect   
 * @Description:aop 拦截日志
 * @author: mhuang
 * @date:   2017年8月15日 下午12:31:03
 */
@Component
@Aspect
@Order(0)
public class WebLogAspect {

	@Pointcut("execution(public * com.petecat..*Controller.*(..))|| execution(public * com.petecat..*Client.*(..))|| @annotation(com.petecat.interchan.logger.common.annotation.LogLogger)")
	private void webLog() { }  

	//临时存放对象
	private ThreadLocal<EsOperatorLogger> tLocal = ThreadLocal.withInitial(() -> null);
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private EsLoggerDao esLoggerDao;
	
	@Value("${spring.application.name}")
	private String application;
	
	@Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
		//logger.debug("===es日志====正在写入数据===");
		//TODO 改为异步处理
		EsOperatorLogger eslogger = null;
		 // 接收到请求，记录请求内容

		try {
			try{
				if (Class.forName("org.springframework.web.context.request.RequestContextHolder") != null){
					ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
					if(attributes != null){
			        	 HttpServletRequest request = attributes.getRequest();
			             eslogger = LoggerUtils.getEsLogger(request,joinPoint);
			        }
				}
			}catch(Exception e){
				
			}
		if(eslogger == null)
			eslogger = LoggerUtils.getEsLogger(joinPoint);
		    tLocal.set(eslogger);
		}catch(Exception e1) {
			this.setExceptionData(eslogger,e1);
			esLoggerDao.insertAsync(eslogger, application, application);
			tLocal.remove();
			throw e1;
		}
        //logger.debug("===es日志====数据写入完毕===");
	}
	
	/** 
	 *   
	 * @Title: setExceptionData
	 * @param eslogger
	 * @param e1
	 * @return void     
	 */
	private void setExceptionData(EsOperatorLogger esLogger, Throwable ex) {
		if(esLogger == null) {
			return;
		}
		LocalDateTime now = LocalDateTime.now();
		esLogger.setEndDate(DateTimeUtils.localDateTimeToDate(now));
		esLogger.setEndDateFormatter(now.toString()+"+08:00");
		esLogger.setStatus(2);
		
		esLogger.setEMsg(ex.toString());
		StackTraceElement[] stes=ex.getStackTrace();
		StringBuilder errDetailMsg=new StringBuilder();
		if(stes!=null && stes.length>0)
		{
			for (StackTraceElement ste:stes)
			{
				errDetailMsg.append(ste.toString());
				errDetailMsg.append(System.getProperty("line.separator"));
			}
		}
		esLogger.setEDetailMsg(errDetailMsg.toString());
		
	}

	@AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
		//logger.debug("===es日志====正在写入返回数据===");
		EsOperatorLogger esLogger = tLocal.get();
		LocalDateTime now = LocalDateTime.now();
		esLogger.setEndDate(DateTimeUtils.localDateTimeToDate(now));
		esLogger.setEndDateFormatter(now.toString()+"+08:00");
		esLogger.setStatus(1);
		esLogger.setRestData(JSON.toJSONString(ret));
		esLoggerDao.updateAsync(esLogger,application,application);
		tLocal.remove();
		//logger.debug("===es日志====返回数据写入完毕===");
    }
	
	@AfterThrowing(value = "webLog()",throwing = "ex")  
	public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable ex){ 
		//logger.debug("===es日志====正在写入异常数据===");
		EsOperatorLogger esLogger = tLocal.get();
		this.setExceptionData(esLogger, ex);
		esLoggerDao.updateAsync(esLogger,application,application);
		tLocal.remove();
		//logger.debug("===es日志====异常数据写入完毕===");
	}  
}
