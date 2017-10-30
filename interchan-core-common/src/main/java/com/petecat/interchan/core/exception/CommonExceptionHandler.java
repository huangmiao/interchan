package com.petecat.interchan.core.exception;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petecat.interchan.protocol.Result;

/**
 * 
 * @ClassName:  ExceptionHandler   
 * @Description:通用异常类
 * @author: mhuang
 * @date:   2017年7月12日 上午9:15:20
 */
@ControllerAdvice
public class CommonExceptionHandler {
	
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result<?> defaultErrorHandler(HttpServletRequest request, Exception e) {
		
		logger.error("---Exception Handler---Host {} invokes url {} ERROR: {}", request.getRemoteHost(), request.getRequestURL(), e.getMessage());
		
		Result<?> result = new Result<>();
		
		if(e instanceof BusinessException){
			BusinessException business = (BusinessException) e;
			result.setCode(business.getCode());
			result.setMessage(business.getMessage());
		}else{
			result.setCode(Result.SYS_FAILD);
			result.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		
		return result;
	}
}
