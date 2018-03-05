package com.petecat.interchan.protocol.auth;

import java.io.Serializable;

import lombok.Data;

/**
 * @author 
 */
@Data
public class AuthUrl implements Serializable {

    /**
     * 可访问地址
     */
    private String url;

}