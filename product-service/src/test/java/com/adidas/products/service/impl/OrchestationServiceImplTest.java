package com.adidas.products.service.impl;

import com.adidas.products.exception.ProductAPIException;
import com.adidas.products.service.ClientService;
import com.adidas.products.service.OrchestationService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrchestationServiceImplTest {
  @Autowired
  OrchestationService instance;

  @MockBean(name = "productClientService")
  private ClientService productClientService;

  @MockBean(name = "reviewClientService")
  private ClientService reviewClientService;


  String mockResponseAdidasApi = "{\"id\": \"1234\",\n" +
      "    \"name\": \"Stan Smith Shoes\",\n" +
      "    \"model_number\": \"ION05\",\n" +
      "    \"product_type\": \"inline\",\n" +
      "    \"meta_data\": {\n" +
      "        \"page_title\": \"adidas Stan Smith Shoes - White | adidas UK\",\n" +
      "        \"site_name\": \"adidas United Kingdom\",\n" +
      "        \"description\": \"Shop for Stan Smith Shoes - White at adidas.co.uk! See all the styles and colours of Stan Smith Shoes - White at the official adidas UK online store.\",\n" +
      "        \"keywords\": \"Stan Smith Shoes\",\n" +
      "        \"canonical\": \"//www.adidas.co.uk/stan-smith-shoes/M20324.html\"\n" +
      "    }}";
  String mockResponseReview = "{\n" +
      "\"product_id\":\"M20324\",\n" +
      "\"average_review_score\" : 1.33,\n" +
      "\"number_of_reviews\" : 12344\n" +
      "}";

  @TestConfiguration
  static class DefaultOrchestationServiceImplTestTestContextConfiguration {

    @Bean
    public OrchestationService orchestationService() {
      return new OrchestationServiceImpl();
    }
  }

  @Before
  public void setUp() {
    CompletableFuture<JSONObject> productCompletableFuture
        = CompletableFuture.supplyAsync(() -> new JSONObject(mockResponseAdidasApi));
    CompletableFuture<JSONObject> reviewCompletableFuture
        = CompletableFuture.supplyAsync(() -> new JSONObject(mockResponseReview));

    when(
        productClientService.asyncGet("123")
    ).thenReturn(
        productCompletableFuture
            .thenApplyAsync(s -> s)
    );

    when(
        productClientService.asyncGet("456")
    ).thenReturn(
        productCompletableFuture
            .thenApplyAsync(s -> s)
    );

    when(
        productClientService.asyncGet("789")
    ).thenThrow(
        new HttpClientErrorException(HttpStatus.NOT_FOUND, "sdssd")
    );
    when(
        reviewClientService.get("123")
    ).thenReturn(
        new JSONObject(mockResponseReview)
    );

    when(
        reviewClientService.get("456")
    ).thenReturn(
        null
    );

  }

  @Test
  public void testGetProductExtended_WhenProductExist_ThenReturnAsExpected() throws ProductAPIException {
    JSONObject result = instance.getProductExtended("123");
    assertEquals("ION05", result.getString("model_number"));
    assertEquals("Stan Smith Shoes", result.getString("name"));
    assertEquals(12344, result.getJSONObject("review").getInt("number_of_reviews"));
    assertFalse(result.getJSONObject("review").has("product_id"));
  }

  @Test
  public void testGetProductExtended_WhenReviewDoesNotExist_ThenReturnAsExpected() throws ProductAPIException {
    JSONObject result = instance.getProductExtended("456");
    assertEquals("ION05", result.getString("model_number"));
    assertEquals("Stan Smith Shoes", result.getString("name"));
    assertFalse(result.has("review"));
  }

  @Test
  public void testGetProductExtended_WhenProductDoesNotExist_ThenReturnException() throws ProductAPIException {
    try {
      JSONObject result = instance.getProductExtended("789");
      fail();
    } catch (HttpClientErrorException e) {
      assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
    }
  }
}
