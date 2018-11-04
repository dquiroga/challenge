package com.adidas.products.service.impl;

import com.adidas.products.service.ClientService;
import com.adidas.products.service.OrchestrationService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class OrchestrationServiceimpl implements OrchestrationService {

  private static final Logger logger = LoggerFactory.getLogger(OrchestrationServiceimpl.class);
  private final String PRODUCT_ID_KEY = "product_id";

  @Autowired
  @Qualifier("productClientService")
  ClientService productClientService;

  @Autowired
  @Qualifier("reviewClientService")
  ClientService reviewClientService;

  @Override
  public JSONObject getProductExtended(String productId) {
    logger.info("[ASYNC] Start Async process to generate Extended Product View");
    CompletableFuture<JSONObject> product = productClientService.get(productId);
    CompletableFuture<JSONObject> review = reviewClientService.get(productId);
    try {
      CompletableFuture.allOf(product, review).join();
      review.get().remove(PRODUCT_ID_KEY);
      logger.info("[ASYNC] Finish Async process to generate Extended Product View");
      return product.get().put("review", review.get());
    } catch (Throwable e) {
      e.printStackTrace();
    }
    return new JSONObject();
  }
}
