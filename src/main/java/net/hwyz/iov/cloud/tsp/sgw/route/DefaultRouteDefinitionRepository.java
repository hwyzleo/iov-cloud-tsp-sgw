package net.hwyz.iov.cloud.tsp.sgw.route;

import cn.hutool.core.collection.ListUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 默认路由仓库
 *
 * @author hwyz_leo
 */
@Slf4j
@Component
public class DefaultRouteDefinitionRepository implements RouteDefinitionRepository, ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;
    private List<RouteDefinition> routeDefinitionList = new ArrayList<>();

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @PostConstruct
    public void init() {
        load();
    }

    /**
     * 监听事件刷新配置
     */
    @EventListener
    public void listenEvent(RefreshRoutesEvent event) {
        load();
//        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 加载
     */
    private void load() {
        try {
            List<RouteDefinition> list = new ArrayList<>();
            // 测试阶段先手动添加
            list.add(addRoute("/mp/rvc/action/findVehicle", "rvc-service", "/mp/rvc/action/findVehicle", ListUtil.of("Authentication")));
            list.add(addRoute("/mp/rvc/findVehicle", "rvc-service", "/mp/rvc/findVehicle", ListUtil.of("Authentication")));
            list.add(addRoute("/mp/rvc/cmd", "rvc-service", "/mp/rvc/cmd", ListUtil.of("Authentication")));
            list.add(addRoute("/mp/login/action/sendSmsVerifyCode", "account-service", "/mp/login/action/sendSmsVerifyCode",
                    ListUtil.of()));
            list.add(addRoute("/mp/login/action/smsVerifyCodeLogin", "account-service", "/mp/login/action/smsVerifyCodeLogin",
                    ListUtil.of()));
            list.add(addRoute("/mp/account/info", "account-service", "/account/mp/account/info",
                    ListUtil.of("StripPrefix=1", "Authentication")));
            list.add(addRoute("/mp/account/action/modifyNickname", "account-service", "/account/mp/account/action/modifyNickname",
                    ListUtil.of("StripPrefix=1", "Authentication")));
            list.add(addRoute("/mp/account/action/modifyGender", "account-service", "/account/mp/account/action/modifyGender",
                    ListUtil.of("StripPrefix=1", "Authentication")));
            list.add(addRoute("/mp/account/action/generateAvatarUrl", "account-service", "/account/mp/account/action/generateAvatarUrl",
                    ListUtil.of("StripPrefix=1", "Authentication")));
            list.add(addRoute("/mp/account/action/modifyAvatar", "account-service", "/account/mp/account/action/modifyAvatar",
                    ListUtil.of("StripPrefix=1", "Authentication")));
            routeDefinitionList = list;
            logger.info("路由配置已加载,加载条数:{}", routeDefinitionList.size());
        } catch (Exception e) {
            logger.error("从文件加载路由配置异常", e);
        }
    }

    /**
     * 添加路由
     *
     * @param id      唯一ID
     * @param service 后端服务
     * @param path    映射路径
     * @param filters 插件列表
     * @return 路由
     */
    private RouteDefinition addRoute(String id, String service, String path, List<String> filters) {
        RouteDefinition route = new RouteDefinition();
        route.setId(id);
        route.setUri(URI.create("lb://" + service));
        route.setPredicates(ListUtil.of(new PredicateDefinition("Path=" + path)));
        route.setFilters(filters.stream().map(FilterDefinition::new).collect(Collectors.toList()));
        return route;
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return Mono.defer(() -> Mono.error(new NotFoundException("Unsupported operation")));
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return Mono.defer(() -> Mono.error(new NotFoundException("Unsupported operation")));
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return Flux.fromIterable(routeDefinitionList);
    }

}
