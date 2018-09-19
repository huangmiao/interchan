package com.petecat.interchan.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName:  全局header   
 * @Description:用户对象   
 * @author: mhuang
 * @date:   2017年7月12日 上午11:13:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalHeader{

	/**
	 * 用户id
	 */
	private String userId;

    /**
     * 用户类型
     */
	private String type;

    /**
     * 公司id
     */
	private String companyId;

    /**
     * 用户token
     */
	private String token;

    /**
     * 用户ip
     */
	private String ip;

    /**
     * 来源
     */
	private String source;

    /**
     * 扩展字段
     */
	private Map<String,Object> extraMap = new HashMap<>();

    /**
     * 客户端ip
     */
	private String clientIp;
}
