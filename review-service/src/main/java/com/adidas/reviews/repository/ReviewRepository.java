package com.adidas.reviews.repository;

import com.adidas.reviews.model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {
  Integer deleteReviewByProductId(String productId);
  Review findReviewByProductId(String productId);
  @Override
  List<Review> findAll();
}
