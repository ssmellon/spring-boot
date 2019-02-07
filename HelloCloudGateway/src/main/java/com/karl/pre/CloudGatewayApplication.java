package com.karl.pre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages={"org.springframework.http"})
@EnableEurekaClient
public class CloudGatewayApplication {

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
                .route("path_route", r -> r.path("/jd")
                        .uri("http://jd.com:80/").id("jd_route")
                )
                .route("path_route", r -> r.path("/get")
                        .uri("http://httpbin.org"))
                .route("path_route1", r -> r.path("/a")
                        .uri("http://localhost:8080/getUser"))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }
}