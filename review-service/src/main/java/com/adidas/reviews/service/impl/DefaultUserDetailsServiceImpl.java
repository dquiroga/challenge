package com.adidas.reviews.service.impl;

import com.adidas.reviews.model.DefaultUserPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

  //TODO create an user repository
  @Override
  public UserDetails loadUserByUsername(String username) {
    /*DriverDO driver = driverRepository.findByUsername(username);
    if (driver == null) {
      throw new UsernameNotFoundException(username);
    }*/
    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ADMIN"));
    User user = new User("username", "$2a$04$I9Q2sDc4QGGg5WNTLmsz0.fvGv3OjoZyj81PrSFyGOqMphqfS2qKu", authorities);

    return new DefaultUserPrincipal(user);
  }
}