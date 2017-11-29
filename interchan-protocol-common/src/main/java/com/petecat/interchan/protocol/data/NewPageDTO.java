package com.petecat.interchan.protocol.data;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**   
 * @ClassName:  NewPageDTO   
 * @Description:mysql使用的开始行经过了计算，使用时直接get既可。
 *     注意：实例不能copy，会存在重复计算start；如必须copy，可以在目标dto中单独使用start属性。
 * @author: TANG
 * @date:   2017年9月13日 上午11:01:26   
 */  
abstract public class NewPageDTO implements Serializable {
	/**
	 * @Fields start : 开始页数
	 */
	@ApiModelProperty(value="开始页数",required=true)
	private Integer start;

	/**
	 * @Fields rows : 每页行数
	 */
	@ApiModelProperty(value="每页行数,默认10行",required=true)
	private Integer rows=10;

	private static final long serialVersionUID = 1L;

	public Integer getStart() {
		if (start != null && start > 0)
			return (start - 1) * rows;
		else
			return 0;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

}