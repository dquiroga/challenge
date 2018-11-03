package com.adidas.reviews.controller;

import com.adidas.reviews.dto.ReviewDto;
import com.adidas.reviews.model.Review;
import com.adidas.reviews.service.ReviewService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

  @Autowired ReviewService service;


  @Autowired
  private MapperFacade mapper;

  @RequestMapping(method = RequestMethod.GET)
  public List<ReviewDto> getAll(HttpServletRequest request) {
    List<Review> reviewList = service.getAll();
    return mapper.mapAsList(reviewList, ReviewDto.class);
  }

  //@Cacheable(value = "reviews", key = "#productId")
  @RequestMapping(path = "/{productId}", method = RequestMethod.GET)
  public ReviewDto getById(@PathVariable String productId, HttpServletRequest request) {
    Review review = service.getByProductId(productId);
    return mapper.map(review, ReviewDto.class);
  }

  @RequestMapping(method = RequestMethod.POST)
  public ReviewDto create(@RequestBody ReviewDto reviewDto, HttpServletRequest request) {
    Review review = mapper.map(reviewDto, Review.class);
    review = service.createReview(review);
    return mapper.map(review, ReviewDto.class);
  }

  //@CachePut(value = "reviews", key = "#productId")
  @RequestMapping(path = "/{productId}", method = RequestMethod.PUT)
  public ReviewDto update(@PathVariable String productId, @RequestBody ReviewDto reviewDto, HttpServletRequest request) {
    reviewDto.setProductId(productId);
    Review review = mapper.map(reviewDto, Review.class);
    return  mapper.map(service.updateReview(review), ReviewDto.class);
  }

  //@CacheEvict(value = "reviews", allEntries=true)
  @RequestMapping(path = "/{productId}", method = RequestMethod.DELETE)
  public void delete(@PathVariable String productId, HttpServletRequest request) {
    service.deleteReviewByProductId(productId);
  }
}
