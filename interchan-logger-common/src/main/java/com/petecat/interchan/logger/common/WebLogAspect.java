package com.petecat.interchan.logger.common;

import java.util.Date;

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

	@Pointcut("execution(public * com.petecat..*Controller.*(..)) || @annotation(com.petecat.interchan.logger.common.annotation.LogLogger)")
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
		logger.debug("===es日志====正在写入数据===");
		EsOperatorLogger eslogger = null;
		 // 接收到请求，记录请求内容
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
        
        eslogger.setId(
    		esLoggerDao.insert(eslogger,application,application)
		);
        tLocal.set(eslogger);
        logger.debug("===es日志====数据写入完毕===");
	}
	
	@AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
		logger.debug("===es日志====正在写入返回数据===");
		EsOperatorLogger esLogger = tLocal.get();
		esLogger.setEndDate(new Date());
		esLogger.setStatus(1);
		esLogger.setRestData(JSON.toJSONString(ret));
		esLoggerDao.update(esLogger,application,application, esLogger.getId());
		tLocal.remove();
		logger.debug("===es日志====返回数据写入完毕===");
    }
	
	@AfterThrowing(value = "webLog()",throwing = "ex")  
	public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable ex){ 
		logger.debug("===es日志====正在写入异常数据===");
		EsOperatorLogger esLogger = tLocal.get();
		esLogger.setEndDate(new Date());
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
		esLoggerDao.update(esLogger,application,application, esLogger.getId());
		tLocal.remove();
		logger.debug("===es日志====异常数据写入完毕===");
	}  
}
