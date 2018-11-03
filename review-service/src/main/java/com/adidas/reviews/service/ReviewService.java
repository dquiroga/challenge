package com.adidas.reviews.service;

import com.adidas.reviews.model.Review;

import java.util.List;

public interface ReviewService {
  Review createReview(Review review);
  void deleteReviewByProductId(String productId);
  Review updateReview(Review review);
  Review getByProductId(String productId);
  List<Review> getAll();
}
