package com.karl.pre.controller;

import com.alibaba.fastjson.JSON;
import com.karl.pre.model.GatewayPredicateDefinition;
import com.karl.pre.model.GatewayRouteDefinition;
import com.karl.pre.route.DynamicRouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    Environment environment;

    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @PostMapping("/test")
    public String test() {
        RouteDefinition definition = new RouteDefinition();
        definition.setId("app_route");

        PredicateDefinition predicateDefinition = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        predicateDefinition.setName("Path");
        predicateParams.put("pattern", "/app/**");
        predicateDefinition.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicateDefinition));


        FilterDefinition filterDefinition = new FilterDefinition();
        Map<String, String> filterParams = new HashMap<>(8);
        filterDefinition.setName("RewritePath");
        filterParams.put("regex", "/app/(?<segment>.*)");
        filterParams.put("replacement", "/world/$\\{segment}");
        filterDefinition.setArgs(filterParams);
        definition.setFilters(Arrays.asList(filterDefinition));

        URI uri1 = UriComponentsBuilder.fromPath(environment.getProperty("gate.client.world")).build().toUri();
        definition.setUri(uri1);

        return this.dynamicRouteService.add(definition);
    }

    /**
     * 增加路由
     * @param gwdefinition
     * @return
     */
    @PostMapping("/add")
    public String add(@RequestBody GatewayRouteDefinition gwdefinition) {
        try {
            RouteDefinition definition = assembleRouteDefinition(gwdefinition);
            return this.dynamicRouteService.add(definition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        return this.dynamicRouteService.delete(id);
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable String id) {
        return this.dynamicRouteService.delete(id);
    }

    @PutMapping("/update")
    public String update(@RequestBody GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = assembleRouteDefinition(gwdefinition);
        return this.dynamicRouteService.update(definition);
    }

    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition gwdefinition) {
        RouteDefinition definition = new RouteDefinition();
        List<PredicateDefinition> pdList=new ArrayList<>();
        definition.setId(gwdefinition.getId());
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList=gwdefinition.getPredicates();
        for (GatewayPredicateDefinition gpDefinition: gatewayPredicateDefinitionList) {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gpDefinition.getArgs());
            predicate.setName(gpDefinition.getName());
            pdList.add(predicate);
        }
        definition.setPredicates(pdList);
        URI uri = UriComponentsBuilder.fromHttpUrl(gwdefinition.getUri()).build().toUri();
        definition.setUri(uri);
        return definition;
    }


    @GetMapping("/mono")
    public Mono<String> hello()
    {
        return Mono.just("Welcome to reactive world ~");
    }

}