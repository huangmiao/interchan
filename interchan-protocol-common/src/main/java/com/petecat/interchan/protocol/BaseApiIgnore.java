package com.petecat.interchan.protocol;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: BaseApiIgnore
 * @Description:通用API忽略
 * @author: mhuang
 * @date: 2017年7月19日 下午3:51:47
 */
@Data
public class BaseApiIgnore {

    @ApiModelProperty(value = "用户id", hidden = true)
    private String userId;
}
