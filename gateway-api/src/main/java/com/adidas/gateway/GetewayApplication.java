package com.adidas.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GetewayApplication {
  public static void main(String[] args) {
    SpringApplication.run(GetewayApplication.class, args);
  }

  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(p -> p
            .path("/review/**")
            .filters(f->f.rewritePath("/review/(?<segment>.*)","/review/${segment}"))
            .uri("http://localhost:8200"))
        .route(p -> p
            .path("/product/**")
            .filters(f->f.rewritePath("/product/(?<segment>.*)","/product/${segment}"))
            .uri("http://localhost:8100").id("router"))
        .build();
  }

  @RequestMapping("/fallback")
  public Mono<String> fallback() {
    return Mono.just("fallback");
  }
}
