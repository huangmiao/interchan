package com.petecat.interchan.gateway.common.route.model;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @package: com.petecat.interchan.model
 * @author: mhuang
 * @Date: 2018/12/24 14:56
 * @Description:路由断言定义模型
 */
@Data
public class GatewayPredicateDefinition {

    /**
     * 断言对应的Name
     */
    private String name;

    /**
     * 配置的断言规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}
