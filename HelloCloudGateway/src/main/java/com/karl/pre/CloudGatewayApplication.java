package com.karl.pre;

import com.karl.pre.filter.AuthorityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages={"org.springframework.http"})
@EnableEurekaClient
public class CloudGatewayApplication {

    @Autowired
    Environment environment;

    /**
     * 基本的转发
     * 当访问http://localhost:8080/jd
     * 转发到http://jd.com
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //basic proxy
                .route( r -> r.path("/app/**")
                        .filters(f -> f.rewritePath("/app/(?<segment>.*)", "/world/$\\{segment}")
                        .filter(new AuthorityFilter()))
                        .uri(environment.getProperty("gate.client.world")).id("jd_route")
                )
                .route("path_route", r -> r.path("/api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(environment.getProperty("gate.client.hello")))
                .route("canary_v1", r ->
                    r.path("/test")
                            .filters(f -> {
                                f.rewritePath("/test","/v1");
                                r.weight("canary",50);
                                return f;
                            })
                            .uri(environment.getProperty("gate.client.world"))
                )
                .route("canary_v2", r ->
                        r.path("/test")
                                .filters(f -> {
                                    f.rewritePath("/test","/v2");
                                    r.weight("canary",50);
                                    return f;
                                })
                                .uri(environment.getProperty("gate.client.hello"))
                )
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }
}