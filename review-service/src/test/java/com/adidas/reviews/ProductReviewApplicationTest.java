package com.adidas.reviews;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@EnableAutoConfiguration
@SpringBootTest(classes = ProductReviewApplicationTest.class)
@ActiveProfiles({"test"})
public class ProductReviewApplicationTest {
}
