package com.adidas.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class GetewayApplication {
  @Value("${adidas.challenge.service.reviews.host}")
  private String reviewEnpoint;

  @Value("${adidas.challenge.service.product.host}")
  private String productEnpoint;

  public static void main(String[] args) {
    SpringApplication.run(GetewayApplication.class, args);
  }

  @Bean
  public RouteLocator myRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(p -> p
            .path("/review/**")
            .filters(f->f.rewritePath("/review/(?<segment>.*)","/review/${segment}"))
            .uri(reviewEnpoint))
        .route(p -> p
            .path("/products/**")
            .filters(f->f.rewritePath("/products/(?<segment>.*)","/products/${segment}"))
            .uri(productEnpoint))
        .build();
  }

  @RequestMapping("/fallback")
  public Mono<String> fallback() {
    return Mono.just("fallback");
  }
}
