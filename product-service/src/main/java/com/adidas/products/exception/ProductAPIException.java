package com.adidas.products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The product doesn't exist!")
public class ProductAPIException extends Exception{
  public ProductAPIException(String message)
  {
    super(message);
  }
}
