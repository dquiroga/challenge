package com.adidas.products.controller;

import com.adidas.products.exception.ProductAPIException;
import com.adidas.products.service.OrchestationService;
import io.swagger.annotations.Api;
import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@Api(tags = "Product extended view")
@RequestMapping("/products")
public class ProductController {
  private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  OrchestationService orchestrationService;


  @RequestMapping(path = "/{productId}", method = RequestMethod.GET)
  public String getProductExtended(@PathVariable String productId, HttpRequest request) throws ProductAPIException {
    logger.info("[REST-API] Recive request from {}", request.getRequestLine());
    return orchestrationService.getProductExtended(productId).toString();
  }
}