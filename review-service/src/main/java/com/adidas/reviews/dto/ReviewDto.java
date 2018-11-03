package com.adidas.reviews.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


public class ReviewDto implements Serializable {
  @NotNull
  @JsonProperty("product_id")
  String productId;

  @JsonProperty("average_review_score")
  float averageReviewScore;

  @JsonProperty("number_of_reviews")
  Long numberOfReviews;

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public float getAverageReviewScore() {
    return averageReviewScore;
  }

  public void setAverageReviewScore(float averageReviewScore) {
    this.averageReviewScore = averageReviewScore;
  }

  public Long getNumberOfReviews() {
    return numberOfReviews;
  }

  public void setNumberOfReviews(Long numberOfReviews) {
    this.numberOfReviews = numberOfReviews;
  }
}
