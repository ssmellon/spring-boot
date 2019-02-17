package com.karl.pre;

import com.karl.pre.filter.AuthorityFilter;
import com.karl.pre.filter.PathFilter;
import com.karl.pre.filter.RequestRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatReactiveWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.resources.LoopResources;

import java.time.Duration;

@SpringBootApplication(scanBasePackages={"org.springframework.http","com.karl.pre"})
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
//                .route("redirect_route", r -> r.path("/**")
////                                .filters(f -> f.rewritePath("/path/(?<segment>.*)", "/client/${segment}"))
////                        .filter(new AuthorityFilter()))
//                                .uri(environment.getProperty("gate.client.world"))
//                )
                //basic proxy
                .route("limit_test", r -> r.path("/limit")
                        .filters(f ->
                        {
                            f.rewritePath("/limit","/client/hello");
                            f.filter(new RequestRateLimiter(10, 20, Duration.ofMinutes(1)));
                            return f;
                        })
                        .uri(environment.getProperty("gate.client.hello")))
//                .route("path_route", r -> r.path("/path/**")
////                        .filters(f -> f.stripPrefix(1))
//                        .uri(environment.getProperty("gate.client.world")))
//                .route("hello_route", r -> r.path("/path/**")
//                        .filters(f -> f.rewritePath("/path/(?<segment>.*)", "/client/${segment}"))
////                        .filter(new AuthorityFilter()))
//                        .uri(environment.getProperty("gate.client.hello"))
//                )
                .route("path_route", r -> r.path("/api/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(environment.getProperty("gate.client.hello")))
//                .route("canary_v1", r ->
//                    r.path("/test")
//                            .filters(f -> {
//                                return f;
//                                f.rewritePath("/test","/v1");
//                                r.weight("canary",50);
//                            })
//                            .uri(environment.getProperty("gate.client.world"))
//                )
//                .route("canary_v2", r ->
//                        r.path("/test")
//                                .filters(f -> {
//                                    f.rewritePath("/test","/v2");
//                                    r.weight("canary",50);
//                                    return f;
//                                })
//                                .uri(environment.getProperty("gate.client.hello"))
//                )
                .build();
    }

    @Bean
    KeyResolver apiKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }




//    @Bean
//    public ServletWebServerFactory servletContainer() {
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addAdditionalTomcatConnectors(createStandardConnector()); // 添加http
//        return tomcat;
//    }
//
//    private Connector createStandardConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setPort(port);
//        return connector;
//    }
//    @Bean
//    public PathFilter pathFilter()
//    {
//        return new PathFilter();
//    }
}