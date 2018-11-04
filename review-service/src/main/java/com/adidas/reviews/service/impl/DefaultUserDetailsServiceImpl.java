package com.adidas.reviews.service.impl;

import com.adidas.reviews.model.DefaultUserPrincipal;
import com.adidas.reviews.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DefaultUserDetailsServiceImpl implements UserDetailsService {
  private static final Logger logger = LoggerFactory.getLogger(DefaultUserDetailsServiceImpl.class);

  @Autowired
  UserRepository repository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    logger.info("Get user data with USERNAME {}", username);
    com.adidas.reviews.model.User user = repository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ADMIN"));

    return new DefaultUserPrincipal(new User(user.getUsername(), user.getPassword(), authorities));
  }
}