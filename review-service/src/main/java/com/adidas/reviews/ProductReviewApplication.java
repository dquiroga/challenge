package com.adidas.reviews;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Damian Quiroga on 02/11/18.
 */
@SpringBootApplication
@ComponentScan("com.adidas")
public class ProductReviewApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductReviewApplication.class, args);
  }
}
