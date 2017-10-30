package com.petecat.interchan.jwt.model;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @ClassName:  Jwt   
 * @Description:Jwt实体类  
 * @author: mhuang
 * @date:   2017年7月12日 上午11:30:25
 */
@Data
@Component
public class Jwt {
	
	private String clientId = "authToken";
    private String base64Secret = "aHVhbmdtaWFv";
    private String name = "mhuang";
    private String type =  "Authorization";
    private String headerName = "Bearer";
    private int expiresSecond = 60000;
}
