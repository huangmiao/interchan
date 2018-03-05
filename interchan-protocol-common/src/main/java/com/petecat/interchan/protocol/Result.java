package com.petecat.interchan.protocol;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @param <T>
 * @ClassName: Result
 * @Description:通用返回
 * @author: mhuang
 * @date: 2017年7月12日 上午9:23:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    public static final int SUCCESS = 200;
    public static final int SYS_FAILD = 500;
    public static final int SYS_RESULT_FAILD = 202;
    public static final int TOKEN_EXPIRED = 401;
    public static final int TOKEN_IS_VALID = 406;

    private static final String SUCCESS_MSG = "操作成功";
    public static final String FAILD_MSG = "操作失败";
	private static final String TOKEN_EXPIRED_MSG = "Token已经过期";
	private static final String TOKEN_IS_VALID_MSG = "Token无效";

	@ApiModelProperty(value = "返回状态")   
	private int code; //状态码
	
	@ApiModelProperty(value = "返回信息") 
	private String message;//消息
	
	@ApiModelProperty(value = "返回的对象") 
	@JsonInclude(Include.NON_NULL)
	private T data;
	
	public Result(int code,String message){
		this.code = code;
		this.message = message;
	}
	
	public Result<T> success(T data){
		this.code = SUCCESS;
		this.message = SUCCESS_MSG;
		this.data = data;
		return this;
	}
	public Result(T data){
		this.data = data;
	}
	
	public static Result<?> ok(){
		return new Result<>(SUCCESS,SUCCESS_MSG);
	}
	public static Result<?> faild(){
		return new Result<>(SYS_FAILD,FAILD_MSG);
	}
	
	public static Result<?> faild(String msg){
		return new Result<>(SYS_FAILD,msg);
	}
	
	public static Result<?> ok(Object data){
		return new Result<>(SUCCESS,SUCCESS_MSG,data);
	}

	public static Result<?> tokenExpired(){
		return new Result<>(TOKEN_EXPIRED,TOKEN_EXPIRED_MSG);
	}
	public static Result<?> tokenValid(){
		return new Result<>(TOKEN_IS_VALID,TOKEN_IS_VALID_MSG);
	}
}
