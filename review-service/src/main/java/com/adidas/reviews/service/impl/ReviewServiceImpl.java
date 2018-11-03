package com.adidas.reviews.service.impl;

import com.adidas.reviews.model.Review;
import com.adidas.reviews.repository.ReviewRepository;
import com.adidas.reviews.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

  @Autowired ReviewRepository repository;

  @Override
  public Review createReview(Review review) {
    return repository.save(review);
  }

  @Override
  public void deleteReviewByProductId(String productId) {
    repository.deleteReviewByProductId(productId);
  }

  @Override
  public Review updateReview(Review review) {
    Review reviewToUpdate = repository.findReviewByProductId(review.getProductId());
    reviewToUpdate
        .setAverageReviewScore(review.getAverageReviewScore())
        .setNumberOfReviews(review.getNumberOfReviews());
  return repository.save(reviewToUpdate);
  }

  @Override
  public Review getByProductId(String productId) {
    return repository.findReviewByProductId(productId);
  }

  @Override
  public List<Review> getAll() {
    return repository.findAll();
  }
}
