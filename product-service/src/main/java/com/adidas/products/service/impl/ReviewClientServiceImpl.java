package com.adidas.products.service.impl;

import com.adidas.products.exception.ProductAPIException;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
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
  public CompletableFuture<JSONObject> asyncGet(String Id) {
    return null;
  }

  @Override
  public JSONObject get(String Id){
    String url = String.format("http://".concat(reviewNameService).concat("/review/%s"),Id);
    try{
      ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
      return new JSONObject(response.getBody());
    }catch(RestClientException e){
      logger.warn("Review service is down!");
    }catch (Exception e){
      logger.warn(e.getMessage());
    }

    return null;
  }

}
