package com.petecat.interchan.protocol.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @param <T>
 * @ClassName: Page
 * @Description:查询对象
 * @author: mhuang
 * @date: 2017年7月18日 下午3:39:17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Page<T> extends PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private T record;
}
