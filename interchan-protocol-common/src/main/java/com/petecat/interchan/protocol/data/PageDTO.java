package com.petecat.interchan.protocol.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @ClassName:  Page   
 * @Description:分页 
 * @author: mhuang
 * @date:   2017年7月17日 下午4:41:52
 */
public class PageDTO {

	@Setter
	@Getter
	@ApiModelProperty(value="每页行数,默认10行",required=true)
	Integer rows = 10;//行数
	
	@Setter
	@ApiModelProperty(value="开始页数",required=true)
	Integer start;//条数
	
	public Integer getStart() {
		if (start != null && start > 0)
			return (start - 1) * rows;
		else
			return 0;
	}
}
