package com.petecat.interchan.core.controller;


import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.petecat.interchan.core.exception.BusinessException;
import com.petecat.interchan.protocol.GlobalHeader;
import com.petecat.interchan.protocol.Result;


/**
 * 
 * @ClassName:  BaseController   
 * @Description:通用Controller 
 * @author: mhuang
 * @date:   2017年7月13日 下午3:36:23
 */
public abstract class BaseController {

	/**   
	 * @Title: getUserInfo   
	 * @Description: 获取用户信息
	 * @param jsonHeadUser
	 * @param nullThrowException 为null时要不要抛出异常
	 * @return GlobalHeader     
	 */
	public GlobalHeader getUserInfo(String jsonHeadUser,boolean nullThrowException){
		GlobalHeader header = null;
    	if(StringUtils.isNotBlank(jsonHeadUser)){
	   		 try{
	   			header = JSON.parseObject(jsonHeadUser,GlobalHeader.class);
	   		 }catch(Exception e){
	   		 }
		}
    	if( header == null || StringUtils.isBlank(header.getUserId() ) && nullThrowException){
    		throw new  BusinessException(Result.tokenValid().getCode(),
    				Result.tokenValid().getMessage());
    	}
		 return header;
	}
}
