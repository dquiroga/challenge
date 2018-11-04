package com.adidas.reviews.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The product review that you want create aleady exists, please update instead of create")
public class ProductReviewAlreadyExist extends Exception{
  public ProductReviewAlreadyExist(String message)
  {
    super(message);
  }
}
