package com.petecat.interchan.protocol.auth;

import java.io.Serializable;

import lombok.Data;

/**
 * @author 
 */
@Data
public class AuthExcludeUrl implements Serializable {
    /**
     * 主键
     */
    private String type;

    /**
     * 可访问地址
     */
    private String url;

}