package com.adidas.reviews.model;

import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "reviews")
public class Review implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @Column(unique = true)
  String productId;

  float averageReviewScore;

  Long numberOfReviews;

  public Review() {}

  public Review(String productId) {
    this.productId = productId;
  }


  public float getAverageReviewScore() {
    return averageReviewScore;
  }

  public Review setAverageReviewScore(float averageReviewScore) {
    this.averageReviewScore = averageReviewScore;
    return this;
  }

  public Long getNumberOfReviews() {
    return numberOfReviews;
  }

  public Review setNumberOfReviews(Long numberOfReviews) {
    this.numberOfReviews = numberOfReviews;
    return this;
  }

  public Long getId() {
    return id;
  }

  public Review setId(Long id) {
    this.id = id;
    return this;
  }

  public String getProductId() {
    return productId;
  }

  public Review setProductId(String productId) {
    this.productId = productId;
    return this;
  }
}
