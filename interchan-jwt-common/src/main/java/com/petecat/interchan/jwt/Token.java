package com.petecat.interchan.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.petecat.interchan.jwt.model.Jwt;

import io.jsonwebtoken.Claims;

import java.util.Map;

/**
 * 
 * @ClassName:  Token   
 * @Description:Token生成工具 
 * @author: mhuang
 * @date:   2017年10月27日 上午10:49:36
 */
@Component
public class Token {

	@Autowired
    private Jwt jwt;
	
	public String create(String userId,String type){
		return JwtHelper.createJWT(userId,type, jwt.getClientId(), jwt.getName(), jwt.getExpiresSecond()* 1000, jwt.getBase64Secret());
	}
	
	public String create(String userId,String type,String companyId){
		return JwtHelper.createJWT(userId,type,companyId, jwt.getClientId(), jwt.getName(), jwt.getExpiresSecond()* 1000, jwt.getBase64Secret());
	}

	public Map<String, Object> parse(String jsonWebToken) throws Exception {
		return JwtHelper.parseJWT(jsonWebToken,jwt.getBase64Secret());
	}

	public String refresh(Claims claims){
		return JwtHelper.refreshJWT(claims, jwt.getClientId(), jwt.getName(), jwt.getExpiresSecond()* 1000, jwt.getBase64Secret());
	}
}
