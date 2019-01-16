package com.petecat.interchan.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: JwtHelper
 * @Description:jwt帮助类
 * @author: mhuang
 * @date: 2017年7月12日 上午11:24:31
 */
public class JwtHelper {

    /**
     * 解析Token
     *
     * @param jsonWebToken
     * @param base64Security
     * @return
     */
    public static Claims parseJWT(String jsonWebToken, String base64Security) throws Exception {
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))
                .parseClaimsJws(jsonWebToken).getBody();
    }

    /**
     * @param claims
     * @param audience
     * @param issuer
     * @param TTLMills
     * @param base64Security 刷新token
     * @return
     */
    public static String refreshJWT(Claims claims, String audience, String issuer, long TTLMills, String base64Security) {
        return createJWT(claims, audience, issuer, TTLMills, base64Security);
    }

    public static String createJWT(String userId, String type, String audience, String issuer, long TTLMills, String base64Security) {
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("type", type);
        claimMap.put("userId", userId);
        return createJWT(claimMap, audience, issuer, TTLMills, base64Security);
    }

    public static String createJWT(String userId, String type, String companyId, String audience, String issuer, long TTLMills, String base64Security) {
        Map<String, Object> claimMap = new HashMap<>();
        claimMap.put("type", type);
        claimMap.put("userId", userId);
        claimMap.put("companyId", companyId);
        return createJWT(claimMap, audience, issuer, TTLMills, base64Security);
    }

    public static String createJWT(Map<String, Object> claimMap, String audience, String issuer, long TTLMills, String base64Security) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);
        //生成签名密匙
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("type", "JWT")
                .setClaims(claimMap)
                .setIssuer(issuer)
                .setAudience(audience)
                .signWith(signatureAlgorithm, signingKey);
        //添加token过期时间
        if (TTLMills >= 0) {
            long expMills = nowMills + TTLMills;
            Date exp = new Date(expMills);
            builder.setExpiration(exp).setNotBefore(now);
        }
        //生成JWT
        return builder.compact();
    }


}
