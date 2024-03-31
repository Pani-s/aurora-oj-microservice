package com.pani.auroraojgateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author Pani
 * @date Created in 2024/3/17 12:16
 * @description 过滤器for用户鉴权
 */
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private static final String INNER_PATH = "/**/inner/**";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String path = serverHttpRequest.getURI().getPath();
        // 判断路径中是否包含 inner，只允许内部调用
        if (antPathMatcher.match(INNER_PATH, path)) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap("无权限".getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(dataBuffer));
        }
        // 可做：统一权限校验，通过 JWT 获取登录用户信息，这里还是分布式session吧...
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
