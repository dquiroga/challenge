package com.adidas.products.service.impl;

import com.adidas.products.exception.ProductAPIException;
import com.adidas.products.service.ClientService;
import com.adidas.products.service.OrchestationService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.CompletableFuture;

@Service
public class OrchestationServiceImpl implements OrchestationService {

  private static final Logger logger = LoggerFactory.getLogger(OrchestationServiceImpl.class);
  private final String PRODUCT_ID_KEY = "product_id";

  @Autowired
  @Qualifier("productClientService")
  ClientService productClientService;

  @Autowired
  @Qualifier("reviewClientService")
  ClientService reviewClientService;

  @Override
  public JSONObject getProductExtended(String productId) throws ProductAPIException {
    logger.info("[ASYNC] Start Async process to generate Extended Product View");
    CompletableFuture<JSONObject> product = productClientService.asyncGet(productId);
    JSONObject review = reviewClientService.get(productId);
    try {
      if(review!=null) review.remove(PRODUCT_ID_KEY);
      logger.info("[ASYNC] Finish Async process to generate Extended Product View");
      return product.get().put("review", review);
    } catch (Throwable e) {
      logger.warn("[ASYNC] Finish Async process finish with error");
      throw new ProductAPIException(e.getMessage());
    }
  }
}
