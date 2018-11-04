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
public class ProductClientServiceImplTest {
  @Autowired
  ClientService instance;

  @MockBean(name="defaultRestTemplate")
  private RestTemplate restTemplate;

  static {
    System.setProperty("adidas.products.api.url", "http://adidas.api");
  }

  String adidasMockResponse = "{\n" +
      "  \"id\": \"1234\",\n" +
      "  \"name\": \"Stan Smith Shoes\",\n" +
      "  \"model_number\": \"ION05\",\n" +
      "  \"product_type\": \"inline\",\n" +
      "  \"meta_data\": {\n" +
      "    \"page_title\": \"adidas Stan Smith Shoes - White | adidas UK\",\n" +
      "    \"site_name\": \"adidas United Kingdom\",\n" +
      "    \"description\": \"Shop for Stan Smith Shoes - White at adidas.co.uk! See all the styles and colours of Stan Smith Shoes - White at the official adidas UK online store.\",\n" +
      "    \"keywords\": \"Stan Smith Shoes\",\n" +
      "    \"canonical\": \"//www.adidas.co.uk/stan-smith-shoes/M20324.html\"\n" +
      "  }\n" +
      "}";

  @TestConfiguration
  static class DefaultProductClientServiceImplTestContextConfiguration {

    @Bean
    public ClientService clientService() {
      return new ProductClientServiceImpl();
    }
  }

  @Before
  public void setUp() {

    when(
        restTemplate.exchange(
            Matchers.eq("http://adidas.api/1234"),
            Matchers.eq(HttpMethod.GET),
            Matchers.any(HttpEntity.class),
            Matchers.any(Class.class))
    ).thenReturn(
        new ResponseEntity<>(adidasMockResponse,HttpStatus.OK)
    );

    when(
        restTemplate.exchange(
            Matchers.eq("http://adidas.api/456"),
            Matchers.eq(HttpMethod.GET),
            Matchers.any(HttpEntity.class),
            Matchers.any(Class.class))
    ).thenReturn(
        new ResponseEntity<>("{\n" +
            "    \"message\": \"Product not found\"\n" +
            "}",HttpStatus.NOT_FOUND)
    );
  }

  @Test
  public void testGetProductExtended_WhenProductExist_ThenReturnAsExpected() throws ExecutionException, InterruptedException {
    CompletableFuture<JSONObject> response = instance.asyncGet("1234");
    assertEquals("1234", response.get().getString("id"));
  }

  @Test
  public void testGetProductExtended_WhenProductDoesnExist_ThenReturnAsExpected() throws ExecutionException, InterruptedException {
    CompletableFuture<JSONObject> response = instance.asyncGet("456");
    assertEquals("{}",response.get().toString());
  }
}
