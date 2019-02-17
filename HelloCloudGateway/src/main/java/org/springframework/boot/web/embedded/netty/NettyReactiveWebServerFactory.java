//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.boot.web.embedded.netty;

import java.net.InetSocketAddress;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.server.AbstractReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.util.Assert;
import reactor.ipc.netty.http.server.HttpServer;
import reactor.ipc.netty.http.server.HttpServerOptions.Builder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NettyReactiveWebServerFactory extends AbstractReactiveWebServerFactory {
    private List<NettyServerCustomizer> serverCustomizers = new ArrayList();
    private Duration lifecycleTimeout;

    public NettyReactiveWebServerFactory() {
    }

    public NettyReactiveWebServerFactory(int port) {
        super(port);
    }

    @Value("${http.port}")
    private Integer port;

    @Autowired
    HttpHandler httpHandler;

    private WebServer http;

    @PostConstruct
    public void start()
    {
        ReactiveWebServerFactory factory = new NettyReactiveWebServerFactory(port);
        this.http = factory.getWebServer(this.httpHandler);
        this.http.start();
    }

    @PreDestroy
    public void stop() {
        this.http.stop();
    }

    public WebServer getWebServer(HttpHandler httpHandler) {
        HttpServer httpServer = this.createHttpServer();
        ReactorHttpHandlerAdapter handlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
        return new NettyWebServer(httpServer, handlerAdapter, this.lifecycleTimeout);
    }

    public Collection<NettyServerCustomizer> getServerCustomizers() {
        return this.serverCustomizers;
    }

    public void setServerCustomizers(Collection<? extends NettyServerCustomizer> serverCustomizers) {
        Assert.notNull(serverCustomizers, "ServerCustomizers must not be null");
        this.serverCustomizers = new ArrayList(serverCustomizers);
    }

    public void addServerCustomizers(NettyServerCustomizer... serverCustomizers) {
        Assert.notNull(serverCustomizers, "ServerCustomizer must not be null");
        this.serverCustomizers.addAll(Arrays.asList(serverCustomizers));
    }

    public void setLifecycleTimeout(Duration lifecycleTimeout) {
        this.lifecycleTimeout = lifecycleTimeout;
    }

    private HttpServer createHttpServer() {
        return HttpServer.builder().options((options) -> {
            options.listenAddress(this.getListenAddress());
            if (this.getSsl() != null && this.getSsl().isEnabled()) {
                SslServerCustomizer sslServerCustomizer = new SslServerCustomizer(this.getSsl(), this.getSslStoreProvider());
                sslServerCustomizer.customize(options);
            }

            if (this.getCompression() != null && this.getCompression().getEnabled()) {
                CompressionCustomizer compressionCustomizer = new CompressionCustomizer(this.getCompression());
                compressionCustomizer.customize(options);
            }

            this.applyCustomizers(options);
        }).build();
    }

    private InetSocketAddress getListenAddress() {
        return this.getAddress() != null ? new InetSocketAddress(this.getAddress().getHostAddress(), this.getPort()) : new InetSocketAddress(this.getPort());
    }

    private void applyCustomizers(Builder options) {
        this.serverCustomizers.forEach((customizer) -> {
            customizer.customize(options);
        });
    }
}
