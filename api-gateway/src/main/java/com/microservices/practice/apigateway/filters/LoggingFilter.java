package com.microservices.practice.apigateway.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    private Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    /*Implementing a global filter so each hit to cloud api gateway will path throw this
    so we can implement in similar manner if we want to handle global security and other
            global configuration to be mapped on each request*/

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        logger.info("Path of the request received -> {}",exchange.getRequest().getPath());
        return chain.filter(exchange);
    }
}
