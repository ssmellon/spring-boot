package com.karl.pre.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//import org.springframework.stereotype.Component;
//
//@Component
public class AuthorityFilter implements GatewayFilter, Ordered
{
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().get("X-Auth-Token").get(0);
        System.out.println("<<<<<<<<<<<<<------------->>>>>>>>>>>>>");
        System.out.println(token);
        System.out.println("<<<<<<<<<<<<<------------->>>>>>>>>>>>>");
        return chain.filter(exchange);
    }

    public int getOrder()
    {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
