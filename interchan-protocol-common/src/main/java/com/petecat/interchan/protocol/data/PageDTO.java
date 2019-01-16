package com.petecat.interchan.protocol.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: Page
 * @Description:分页
 * @author: mhuang
 * @date: 2017年7月17日 下午4:41:52
 */
@Data
public class PageDTO {

    @ApiModelProperty(value = "每页行数,默认10行", required = true)
    Integer rows = 10;

    @ApiModelProperty(value = "开始页数", required = true)
    Integer start = 1;
}
