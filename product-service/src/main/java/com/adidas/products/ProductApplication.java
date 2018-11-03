package com.adidas.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Damian Quiroga on 02/11/18.
 */
@SpringBootApplication
@ComponentScan("com.adidas")
@EnableEurekaClient
public class ProductApplication {
  public static void main(String[] args) {
    SpringApplication.run(ProductApplication.class, args);
  }
}
