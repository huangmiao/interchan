package com.petecat.interchan.gateway.common.route.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * @package: com.petecat.interchan.route.service
 * @author: mhuang
 * @Date: 2018/12/24 15:02
 * @Description:
 */
public interface DynamicRouteService {

    /**
     * 添加路由
     *
     * @param definition
     * @return
     */
    void add(RouteDefinition definition);

    /**
     * 修改路由
     *
     * @param definition
     * @return
     */
    void update(RouteDefinition definition);

    /**
     * 删除路由
     *
     * @param id
     * @return
     */
    void delete(String id);
}
