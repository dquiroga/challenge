package com.adidas.reviews.service;

import com.adidas.reviews.exceptions.ConstraintsViolationException;
import com.adidas.reviews.exceptions.ReviewNotFoundException;
import com.adidas.reviews.model.Review;

import java.util.List;

public interface ReviewService {
  Review createReview(Review review) throws ConstraintsViolationException;
  void deleteReviewByProductId(String productId) throws ReviewNotFoundException;
  Review updateReview(Review review) throws ReviewNotFoundException;
  Review getByProductId(String productId) throws ReviewNotFoundException;
  List<Review> getAll();
}
