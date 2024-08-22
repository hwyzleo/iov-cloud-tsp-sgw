package net.hwyz.iov.cloud.tsp.sgw.service;

import org.springframework.cloud.gateway.route.RouteDefinition;

/**
 * 动态路由服务
 *
 * @author hwyz_leo
 */
public interface DynamicRouteService {

    /**
     * 增加路由
     *
     * @param definition 路由
     */
    void add(RouteDefinition definition);

    /**
     * 更新路由
     *
     * @param definition 路由
     */
    void update(RouteDefinition definition);

    /**
     * 删除路由
     *
     * @param id 路由ID
     */
    void delete(String id);

}
