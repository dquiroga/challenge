package com.adidas.products.controller;

import com.adidas.products.service.OrchestrationService;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  OrchestrationService orchestrationService;


  @RequestMapping(method = RequestMethod.PUT)
  public String get(){
    return "Hello World!!";
  }

  @RequestMapping(path = "/{productId}", method = RequestMethod.GET)
  public String getProductExtended(@PathVariable String productId, HttpRequest request){
    logger.info("[REST-API] Recive request from {}", request.getRequestLine());
    return orchestrationService.getProductExtended(productId).toString();
  }
}