package com.adidas.reviews.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User  implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  String username;
  String password;

  public User() {
  }

  public String getUsername() {
    return username;
  }

  public User setUsername(String username) {
    this.username = username;
    return this;
  }

  public Long getId() {
    return id;
  }

  public User setId(Long id) {
    this.id = id;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public User setPassword(String password) {
    this.password = password;
    return this;
  }
}
