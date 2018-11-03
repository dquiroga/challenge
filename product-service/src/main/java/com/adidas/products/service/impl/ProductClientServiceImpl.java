package com.adidas.products.service.impl;

import com.adidas.products.service.ClientService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Qualifier("productClientService")
public class ProductClientServiceImpl implements ClientService {
  private static final Logger logger = LoggerFactory.getLogger(ProductClientServiceImpl.class);

  @Autowired
  @Qualifier("defaultRestTemplate")
  RestTemplate restTemplate;

  @Value("${adidas.products.api.url}")
  String productApiUrl;

  /**
   * This method generate custom headers for Adidas API
   *
   * @return
   */
  private HttpHeaders getHeaders() {
    HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.set("cookie", "troute=t1;");
    requestHeaders.set("user-agent", "Mozilla");
    return requestHeaders;
  }

  @Async
  @Override
  public CompletableFuture<JSONObject> get(String Id) {
    String url = String.format(productApiUrl.concat("/%s"), Id);
    JSONObject json = new JSONObject();
    logger.info("[ASYNC] Calling Adidas endpoint with URL => {} ",url);
    ResponseEntity<String> response =
        restTemplate.exchange(
            url, HttpMethod.GET, new HttpEntity(null, getHeaders()), String.class);
    logger.info("[ASYNC] Responose Status from Adidas endpoint with URL => {}  - {} ", url, response.getStatusCode() );

    if (response.getStatusCode().is2xxSuccessful()) json = new JSONObject(response.getBody());

    return CompletableFuture.completedFuture(json);
  }
}
