package com.microservices.practice.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    /*This is custom route configuration. If we don't write this then we can enable discovery locator in application.properties
        If you don't need custom routes then the url of api gateway should prefix with service name in eureka followed by rest url
            #Below will enable autodiscovery to use eureka service name followed by url or you can define custom routes
            #http://localhost:8765/CURRENCY-CONVERSION/currency-conversion/feign/from/USD/to/INR/quantity/10
            #spring.cloud.gateway.discovery.locator.enabled=true
            #spring.cloud.gateway.discovery.locator.lower-case-service-id=true*/

    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder builder){

        /*Below is url routes .path indicates the url pattern and .uri method will automatically add eureka load balanced service name
                as prefix to the incoming url ex: lb://CURRENCY-EXCHANGE-SERVICE (application name registered on eureka)
                 we can also rewrite the url with the filters methods ex /currency-conversion-new is
                converted to /currency-conversion/feign*/

        return builder.routes()
                .route(p -> p.path("/get")
                             .filters(f-> f.addRequestHeader("token","Bearer 1233")
                                            .addRequestParameter("param","myvalue"))
                             .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**")
                              .uri("lb://currency-exchange"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion/feign/**")
                        .uri("lb://currency-conversion"))
                .route(p -> p.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion/feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();

    }
}
