package com.karl.pre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;

import java.time.Duration;

@SpringBootApplication(scanBasePackages={"org.springframework.http","com.karl.pre"})
@EnableEurekaClient
public class CloudGatewayApplication {

    @Autowired
    Environment environment;

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("hello_route", r -> r.path("/path/**")
//                        .filters(
//                                f -> {
//                                    f.rewritePath("/path/(?<segment>.*)", "/${segment}");
//                                    return f;
//                                })
//                        .uri(environment.getProperty("gate.client.hello"))
//                )
//                .build();
//    }

    public static void main(String[] args)
    {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }

}