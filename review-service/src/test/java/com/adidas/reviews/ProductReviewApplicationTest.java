package com.adidas.reviews;

import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@EnableAutoConfiguration
@SpringBootTest(classes = ProductReviewApplicationTest.class)
@ActiveProfiles({"test"})
public class ProductReviewApplicationTest {
}
