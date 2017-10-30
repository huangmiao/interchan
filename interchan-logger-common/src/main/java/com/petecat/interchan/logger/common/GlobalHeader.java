package com.petecat.interchan.logger.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

	private String userId;//用户id
	
	private String type;//用户类型
	
	private String companyId;//公司id
	
	private String token;//用户token
	
	private String ip;//用户ip
}
