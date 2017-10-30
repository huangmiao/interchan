package com.petecat.interchan.protocol.data;

import java.util.Collections;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @ClassName:  PageVO   
 * @Description:分页返回  
 * @author: mhuang
 * @date:   2017年7月18日 上午10:58:14   
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {

	 /**
     * 总行数
     */
	@ApiModelProperty(value = "总条数")
    private int totalSize;

    /**
     * 业务数据 <LIST>
     */
	@ApiModelProperty(value = "业务数据")
    private List<T> result = Collections.emptyList();
}
