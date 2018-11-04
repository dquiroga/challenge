package com.adidas.reviews.controller;

import com.adidas.reviews.ProductReviewApplicationTest;
import com.adidas.reviews.config.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ProductReviewApplicationTest.class})
@ActiveProfiles({"base", "ci"})
public class ReviewControllerTest {


  @Autowired
  private TestRestTemplate restTemplate;

  @Before
  public void setup(){
  }

  @Test
  public void testGetAll_whenXX_thenReturnAsExpected() throws Exception {
  }

}
