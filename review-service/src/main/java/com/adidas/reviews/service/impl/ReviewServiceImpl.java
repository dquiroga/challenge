package com.adidas.reviews.service.impl;

import com.adidas.reviews.exceptions.ConstraintsViolationException;
import com.adidas.reviews.exceptions.ReviewNotFoundException;
import com.adidas.reviews.model.Review;
import com.adidas.reviews.repository.ReviewRepository;
import com.adidas.reviews.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Primary
public class ReviewServiceImpl implements ReviewService {
  private static final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

  @Autowired
  ReviewRepository repository;

  @Override
  @Transactional
  public Review createReview(Review review) throws ConstraintsViolationException {
    logger.info("Create review object with product id {}", review.getProductId());
    Optional<Review> existedReview = repository.findReviewByProductId(review.getProductId());
    if(existedReview.isPresent()){
      logger.warn("Some constraints are thrown due to review creation");
      throw new ConstraintsViolationException("Some constraints are thrown due to review creation");
    }
    review = repository.save(review);

    return repository.save(review);
  }

  @Override
  @Transactional
  public void deleteReviewByProductId(String productId) throws ReviewNotFoundException {
    logger.info("DELETE review object with product id {}", productId);
    repository.findReviewByProductId(productId).orElseThrow(() -> new ReviewNotFoundException("Could not find entity with id: " + productId));
    repository.deleteReviewByProductId(productId);
  }

  @Override
  @Transactional
  public Review updateReview(Review review) throws ReviewNotFoundException {
    logger.info("UPDATE review object with product id {}", review.getProductId());
    Review reviewToUpdate = repository.findReviewByProductId(review.getProductId())
        .orElseThrow(() -> new ReviewNotFoundException("Could not find entity with id: " + review.getProductId()));

    reviewToUpdate
        .setAverageReviewScore(review.getAverageReviewScore())
        .setNumberOfReviews(review.getNumberOfReviews());

    return repository.save(reviewToUpdate);
  }

  @Override
  public Review getByProductId(String productId) throws ReviewNotFoundException {
    return repository.findReviewByProductId(productId)
              .orElseThrow(() -> new ReviewNotFoundException("Could not find entity with product id: " + productId));
  }

  @Override
  public List<Review> getAll() {
    return repository.findAll();
  }
}
