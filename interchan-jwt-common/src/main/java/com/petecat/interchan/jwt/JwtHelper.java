package com.petecat.interchan.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @ClassName:  JwtHelper   
 * @Description:jwt帮助类  
 * @author: mhuang
 * @date:   2017年7月12日 上午11:24:31
 */
public class JwtHelper {

	/**
	 * 解析Token
	 * @param jsonWebToken
	 * @param base64Security
	 * @return
	 */
	public static Claims parseJWT(String jsonWebToken,String base64Security) throws Exception{
    	return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(jsonWebToken).getBody();
    }
	
	/**
	 * 
	 * @param claims
	 * @param audience
	 * @param issuer
	 * @param TTLMills
	 * @param base64Security
	 * 刷新token
	 * @return
	 */
	public static String refreshJWT(Claims claims,String audience,String issuer,long TTLMills, String base64Security){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        //生成签名密匙
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setHeaderParam("type","JWT")
                .setClaims(claims)
        		.setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm,signingKey);
        //添加token过期时间
        if (TTLMills >=0 ){
            long expMills = nowMills + TTLMills;
            Date exp = new Date(expMills);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
	}
	
    public static String createJWT( String userId,String type, String audience, String issuer, long TTLMills, String base64Security){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        //生成签名密匙
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("type","JWT")
        		.claim("type",type)
                .claim("userId",userId)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm,signingKey);
        //添加token过期时间
        if (TTLMills >= 0){
            long expMills = nowMills + TTLMills;
            Date exp = new Date(expMills);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }
    
    public static String createJWT(String userId,String type,String companyid, String audience, String issuer, long TTLMills, String base64Security){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        //生成签名密匙
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("type","JWT")
        		.claim("type",type)
                .claim("userId",userId)
                .claim("companyId",companyid)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm,signingKey);
        //添加token过期时间
        if (TTLMills >= 0){
            long expMills = nowMills + TTLMills;
            Date exp = new Date(expMills);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }
}
