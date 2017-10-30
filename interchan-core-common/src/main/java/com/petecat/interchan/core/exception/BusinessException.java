package com.petecat.interchan.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName:  BusinessException   
 * @Description:通用异常  
 * @author: mhuang
 * @date:   2017年7月11日 下午6:53:41
 */
public class BusinessException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	@Setter
	@Getter 
	private int code; //错误码
	
	@Setter
	@Getter
	private String message;//错误的信息
	
	@Setter
	@Getter
	private Throwable cause; //错误的异常
	
	public BusinessException(int code,String message){
		super(message);
		this.code = code;
		this.message = message;
	}
	
	public BusinessException(int code,String message,Throwable cause){
		super(message,cause);
		this.code = code;
		this.message = message;
		this.cause = cause;
	}
	
}
