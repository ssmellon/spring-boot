//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.web.server.adapter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import java.net.URI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebHandler;
import org.springframework.web.server.handler.WebHandlerDecorator;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;
import org.springframework.web.server.session.DefaultWebSessionManager;
import org.springframework.web.server.session.WebSessionManager;
import reactor.core.publisher.Mono;

public class HttpWebHandlerAdapter extends WebHandlerDecorator implements HttpHandler {
    private static final String DISCONNECTED_CLIENT_LOG_CATEGORY = "org.springframework.web.server.DisconnectedClient";
    private static final Set<String> DISCONNECTED_CLIENT_EXCEPTIONS = new HashSet(Arrays.asList("ClientAbortException", "EOFException", "EofException"));
    private static final Log logger = LogFactory.getLog(HttpWebHandlerAdapter.class);
    private static final Log disconnectedClientLogger = LogFactory.getLog("org.springframework.web.server.DisconnectedClient");
    private WebSessionManager sessionManager = new DefaultWebSessionManager();
    @Nullable
    private ServerCodecConfigurer codecConfigurer;
    @Nullable
    private LocaleContextResolver localeContextResolver;
    @Nullable
    private ApplicationContext applicationContext;

    public HttpWebHandlerAdapter(WebHandler delegate) {
        super(delegate);
    }

    public void setSessionManager(WebSessionManager sessionManager) {
        Assert.notNull(sessionManager, "WebSessionManager must not be null");
        this.sessionManager = sessionManager;
    }

    public WebSessionManager getSessionManager() {
        return this.sessionManager;
    }

    public void setCodecConfigurer(ServerCodecConfigurer codecConfigurer) {
        Assert.notNull(codecConfigurer, "ServerCodecConfigurer must not be null");
        this.codecConfigurer = codecConfigurer;
    }

    public ServerCodecConfigurer getCodecConfigurer() {
        return this.codecConfigurer != null ? this.codecConfigurer : ServerCodecConfigurer.create();
    }

    public void setLocaleContextResolver(LocaleContextResolver localeContextResolver) {
        this.localeContextResolver = localeContextResolver;
    }

    public LocaleContextResolver getLocaleContextResolver() {
        return (LocaleContextResolver)(this.localeContextResolver != null ? this.localeContextResolver : new AcceptHeaderLocaleContextResolver());
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Nullable
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public Mono<Void> handle(ServerHttpRequest request, ServerHttpResponse response) {
        URI uri = request.getURI();
        String path = uri.getPath();
        System.out.println("path ----------- " + path);
        if(path.contains("tested"))
        {
            response.setStatusCode(HttpStatus.BAD_GATEWAY);
            return Mono.defer(response::setComplete);
        }
        ServerWebExchange exchange = this.createExchange(request, response);
        Mono var10000 = this.getDelegate().handle(exchange).onErrorResume(
                (ex) -> { return this.handleFailure(request, response, ex);
        });
        response.getClass();
        return var10000.then(Mono.defer(response::setComplete));

    }

    protected ServerWebExchange createExchange(ServerHttpRequest request, ServerHttpResponse response) {
        return new DefaultServerWebExchange(request, response, this.sessionManager, this.getCodecConfigurer(), this.getLocaleContextResolver(), this.applicationContext);
    }

    private Mono<Void> handleFailure(ServerHttpRequest request, ServerHttpResponse response, Throwable ex) {
        if (this.isDisconnectedClientError(ex)) {
            if (disconnectedClientLogger.isTraceEnabled()) {
                disconnectedClientLogger.trace("Looks like the client has gone away", ex);
            } else if (disconnectedClientLogger.isDebugEnabled()) {
                disconnectedClientLogger.debug("Looks like the client has gone away: " + ex + " (For a full stack trace, set the log category '" + "org.springframework.web.server.DisconnectedClient" + "' to TRACE level.)");
            }

            return Mono.empty();
        } else if (response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR)) {
            logger.error("Failed to handle request [" + request.getMethod() + " " + request.getURI() + "]", ex);
            return Mono.empty();
        } else {
            HttpStatus status = response.getStatusCode();
            logger.error("Unhandled failure: " + ex.getMessage() + ", response already set (status=" + status + ")");
            return Mono.error(ex);
        }
    }

    private boolean isDisconnectedClientError(Throwable ex) {
        String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
        message = message != null ? message.toLowerCase() : "";
        String className = ex.getClass().getSimpleName();
        return message.contains("broken pipe") || DISCONNECTED_CLIENT_EXCEPTIONS.contains(className);
    }
}
