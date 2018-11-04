package com.adidas.products.service;

import com.adidas.products.exception.ProductAPIException;
import org.json.JSONObject;
import org.springframework.web.client.HttpClientErrorException;

public interface OrchestationService {
  JSONObject getProductExtended(String productId) throws HttpClientErrorException, ProductAPIException;
}
