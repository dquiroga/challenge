package com.adidas.products.service.impl;

import com.adidas.products.service.ClientService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ReviewClientServiceImplTest {
  @Autowired
  ClientService instance;

  @MockBean(name="challengeRestTemplate")
  private RestTemplate restTemplate;

  static {
    System.setProperty("adidas.challenge.service.reviews.name", "review-service.api");
  }

  String reviewMockResponse = "{\n" +
      "\"product_id\":\"1234\",\n" +
      "\"average_review_score\" : 1.33,\n" +
      "\"number_of_reviews\" : 12344\n" +
      "}";

  @TestConfiguration
  static class DefaultReviewClientServiceImplTestContextConfiguration {

    @Bean
    public ClientService clientService() {
      return new ReviewClientServiceImpl();
    }
  }

  @Before
  public void setUp() {

    when(
        restTemplate.exchange("http://review-service.api/review/1234",
            HttpMethod.GET,null,String.class)
    ).thenReturn(
        new ResponseEntity<>(reviewMockResponse,HttpStatus.OK)
    );

    when(
        restTemplate.exchange("http://review-service.api/review/456",
            HttpMethod.GET,null,String.class)
    ).thenReturn(
        new ResponseEntity<>("{\n" +
            "    \"message\": \"Product not found\"\n" +
            "}",HttpStatus.NOT_FOUND)
    );
  }

  @Test
  public void testGetProductReview_WhenProductExist_ThenReturnAsExpected() {
    JSONObject response = instance.get("1234");
    assertEquals("1234", response.getString("product_id"));
  }

  @Test
  public void testGetProductReview_WhenProductDoesnExist_ThenReturnAsExpected() {
    JSONObject response = instance.get("456");
    assertEquals("{\"message\":\"Product not found\"}",response.toString());
  }
}
