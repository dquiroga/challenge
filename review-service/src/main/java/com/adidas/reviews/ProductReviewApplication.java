package com.adidas.reviews;


import io.swagger.annotations.Api;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Damian Quiroga on 02/11/18.
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan("com.adidas")
public class ProductReviewApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductReviewApplication.class, args);
  }
  @Bean
  public Docket docket()
  {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(generateApiInfo());
  }

  private ApiInfo generateApiInfo()
  {
    return new ApiInfo("Adidas Coding Challenge", "This service is to check the technology knowledge.", "Version 1.0 - mw",
        "urn:tos", "damian.a.quiroga@gmail.com", "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
  }
}
