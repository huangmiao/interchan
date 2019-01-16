package com.petecat.interchan.jwt;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @package: com.petecat.interchan.jwt
 * @author: mhuang
 * @Date: 2018/12/27 15:32
 * @Description:Jwt配置
 */
@Data
@ToString
@ConfigurationProperties(prefix = "interchan.jwt")
public class JwtProperties {

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * Base64
     */
    private String base64Secret;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * header名
     */
    private String headerName;

    /**
     * 过期时间
     */
    private int expiresSecond;
}
