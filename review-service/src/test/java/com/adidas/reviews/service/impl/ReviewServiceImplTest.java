package com.adidas.reviews.service.impl;

import com.adidas.reviews.exceptions.ConstraintsViolationException;
import com.adidas.reviews.exceptions.ReviewNotFoundException;
import com.adidas.reviews.model.Review;
import com.adidas.reviews.repository.ReviewRepository;
import com.adidas.reviews.service.ReviewService;
import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ReviewServiceImplTest {

  @TestConfiguration
  static class ReviewServiceImplTestContextConfiguration {
    @Bean
    public ReviewService reviewService() {
      return new ReviewServiceImpl();
    }
  }

  @Autowired
  private ReviewServiceImpl instance;

  @MockBean
  private ReviewRepository reviewRepository;

  private final Review mockCreateReview = new Review().setAverageReviewScore(100).setNumberOfReviews(90L).setProductId("M1244");
  private final Review mockExistReview = new Review().setAverageReviewScore(100).setNumberOfReviews(90L).setProductId("M12445");

  @Before
  public void setup() {
    when(
        reviewRepository.save(mockCreateReview)
    ).thenReturn(
        mockCreateReview.setId(1L)
    );
    when(
        reviewRepository.findReviewByProductId("M12445")
    ).thenReturn(
        Optional.of(mockExistReview)
    );
    when(
        reviewRepository.save(mockExistReview.setNumberOfReviews(1569L))
    ).thenReturn(
        mockExistReview.setNumberOfReviews(1569L)
    );
  }

  @Test
  public void testCreateReview_whenReviewDoesntExist_thenReturnAsExpected() throws ConstraintsViolationException {
    Review result = instance.createReview(mockCreateReview);
    assertEquals("M1244", result.getProductId());
    assertEquals((Long) 90L, result.getNumberOfReviews());
  }

  @Test
  public void testCreateReview_whenReviewAlreadyExist_thenReturnConstraintsViolationException() throws ConstraintsViolationException {
    try {
      Review result = instance.createReview(mockExistReview);
      fail();
    } catch (ConstraintsViolationException e) {
      MatcherAssert.assertThat(e.getMessage(), containsString("Some constraints are thrown due to review creation"));
    }
  }

  @Test
  public void testDeleteReviewByProductId_whenReviewDoesNotExist_thenReturnReviewNotFoundException() throws ReviewNotFoundException {
    try {
      instance.deleteReviewByProductId("AA21212");
      fail();
    } catch (ReviewNotFoundException e) {
      MatcherAssert.assertThat(e.getMessage(), containsString("Could not find entity with id: AA21212"));
    }
  }

  @Test
  public void testUpdateReview_whenReviewDoesNotExist_thenReturnReviewNotFoundException() throws ReviewNotFoundException {
    try {
      instance.updateReview(mockCreateReview);
      fail();
    } catch (ReviewNotFoundException e) {
      MatcherAssert.assertThat(e.getMessage(), containsString("Could not find entity with id: M1244"));
    }
  }

  @Test
  public void testUpdateReview_whenReviewExist_thenReturnAsExpected() throws ReviewNotFoundException {
    Review result = instance.updateReview(mockExistReview.setNumberOfReviews(1569L));
    assertEquals("M12445", result.getProductId());
    assertEquals((Long) 1569L, result.getNumberOfReviews());
  }

  @Test
  public void testGetByProductId_whenReviewExist_thenReturnAsExpected() throws ReviewNotFoundException {
    Review result = instance.getByProductId("M12445");
    assertEquals("M12445", result.getProductId());
  }

  @Test
  public void testGetByProductId_whenReviewDoesNotExist_thenReturnReviewNotFoundException() throws ReviewNotFoundException {
    try {
      instance.getByProductId("M124489");
      fail();
    } catch (ReviewNotFoundException e) {
      MatcherAssert.assertThat(e.getMessage(), containsString("Could not find entity with product id: M124489"));
    }
  }
}
