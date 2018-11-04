package com.adidas.products.service;

import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public interface ClientService {
  /**
   * This method is an Asynchronous process to call some
   * APIs and get information about a specific resouce
   *
   * @param Id - resources id
   * @return
   */
  CompletableFuture<JSONObject> asyncGet(String Id);
  JSONObject get(String Id);
}
