package com.adidas.reviews.repository;

import com.adidas.reviews.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
  Integer deleteReviewByProductId(String productId);

  @Override
  Optional<Review> findById(Long aLong);

  Optional<Review> findReviewByProductId(String productId);

  @Override
  List<Review> findAll();


}
