package com.adidas.reviews.repository;

import com.adidas.reviews.model.Review;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles({"base","test"})
public class ReviewRepositoryTest {
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ReviewRepository reviewRepository;

  @Before
  public void setup() {
  }

  @Test
  public void testDeleteReviewByProductId_whenReviewExist_thenReturnAsExpected() {
    Review review = new Review().setAverageReviewScore(100).setNumberOfReviews(90L).setProductId("M1244");
    entityManager.persist(review);
    reviewRepository.deleteReviewByProductId("M1244");
  }

  @Test
  public void testFindReviewByProductId_whenReviewExist_thenReturnAsExpected() {
    Review review = new Review().setAverageReviewScore(100).setNumberOfReviews(90L).setProductId("M1244");
    entityManager.persist(review);
    Review result = reviewRepository.findReviewByProductId("M1244").orElse(null);
    assertEquals("M1244", result.getProductId());
    assertEquals((Long) 90L, result.getNumberOfReviews());
  }

  @Test
  public void testFindById_whenReviewExist_thenReturnAsExpected() {
    Review review = new Review().setAverageReviewScore(100).setNumberOfReviews(90L).setProductId("M1244");
    review = entityManager.persist(review);
    Review result = reviewRepository.findById(review.getId()).orElse(null);
    assertEquals("M1244", result.getProductId());
    assertEquals((Long) 90L, result.getNumberOfReviews());
  }
  @Test
  public void testFindAll_whenReviewExist_thenReturnAsExpected() {
    entityManager.persist(new Review().setAverageReviewScore(100).setNumberOfReviews(90L).setProductId("M1244"));
    entityManager.persist(new Review().setAverageReviewScore(100).setNumberOfReviews(90L).setProductId("M1245"));
    entityManager.persist(new Review().setAverageReviewScore(100).setNumberOfReviews(90L).setProductId("M1246"));

    List<Review> result = reviewRepository.findAll();
    assertThat(result.size(),greaterThanOrEqualTo(3));
  }
}



