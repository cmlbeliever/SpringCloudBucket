package com.cml.learn.springcloud.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/route/def")
public class CustomRouteController {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @RequestMapping("/addRoute")
    public String addRoute(@RequestParam String routeName) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(routeName);
        routeDefinition.setUri(URI.create("lb://USER-SERVICE"));

        PredicateDefinition predicateDefinition = new PredicateDefinition();
        predicateDefinition.setName("Path");
        predicateDefinition.addArg("pattern", "/user-gateway2/*");

        routeDefinition.setPredicates(Arrays.asList(predicateDefinition));

//        FilterDefinition filterDefinition = new FilterDefinition();
//        filterDefinition.setName("AddRequestParameter");
//        Map args = new HashMap();
//        args.put("_genkey_0", "customArg");
//        args.put("_genkey_1", "xxxx" + System.currentTimeMillis());
//        filterDefinition.setArgs(args);
//        routeDefinition.setFilters(Arrays.asList(filterDefinition));

        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();

        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));

        return "新增成功";
    }
}
