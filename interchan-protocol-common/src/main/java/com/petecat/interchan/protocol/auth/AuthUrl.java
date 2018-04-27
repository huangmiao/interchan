package com.petecat.interchan.protocol.auth;

import java.io.Serializable;

import lombok.Data;

/**
 * @author 
 */
@Data
public class AuthUrl implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
     * 可访问地址
     */
    private String url;

}