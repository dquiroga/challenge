package com.adidas.products.service.impl;

import com.adidas.products.service.ClientService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CompletableFuture;

@Service
@Qualifier("reviewClientService")
public class ReviewClientServiceImpl implements ClientService {
  private static final Logger logger = LoggerFactory.getLogger(ReviewClientServiceImpl.class);

  @Autowired
  @Qualifier("challengeRestTemplate")
  RestTemplate restTemplate;

  @Value("${adidas.challenge.service.reviews.name}")
  String reviewNameService;

  @Async
  @Override
  public CompletableFuture<JSONObject> get(String Id) {
    String url = String.format("http://".concat(reviewNameService).concat("/review/%s"),Id);
    JSONObject result = new JSONObject();

    logger.info("[ASYNC] Calling endpoint with URL => {} ",url);
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
    logger.info("[ASYNC] Status from endpoint with URL => {}  - {} ", url, response.getStatusCode() );

    if( response.getStatusCode().is2xxSuccessful() ) result = new JSONObject(response.getBody());

    return CompletableFuture.completedFuture(result);
  }

}
