package com.petecat.interchan.core.entity;

import org.springframework.http.HttpMethod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 
 * 请求封装类   
 * @ClassName:  RequestModel   
 * @author: admin
 * @date:   2018年4月11日 下午4:47:19   
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestModel {
	/**   
	 * @Fields method :请求类型  
	 */   
	private HttpMethod method;
	/**   
	 * @Fields url :服务器地址  
	 */   
	private String url;
	/**   
	 * @Fields sufUrl : 后缀地址
	 */   
	private String sufUrl;
	/**   
	 * @Fields authorization :前端的权限   
	 */   
	private String authorization;
	/**   
	 * @Fields params : 请求的参数 
	 */   
	private Object params;
}
