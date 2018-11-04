package com.adidas.reviews.service.impl;

import com.adidas.reviews.model.User;
import com.adidas.reviews.repository.UserRepository;
import com.adidas.reviews.service.ReviewService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class DefaultUserDetailsServiceImplTest {

  @Autowired
  UserDetailsService instance;

  @MockBean
  private UserRepository userRepository;

  @TestConfiguration
  static class DefaultUserDetailsServiceImplTestContextConfiguration {
    @Bean
    public UserDetailsService reviewService() {
      return new DefaultUserDetailsServiceImpl();
    }
  }

  @Before
  public void setup() {
    User user = new User().setId(1l).setPassword("password").setUsername("username");
    when(
        userRepository.findByUsername("username")
    ).thenReturn(
        Optional.of(user)
    );

  }


  @Test
  public void testLoadUserByUsername_whenCalledWithExistUser_thenReturnAsExpected(){
    UserDetails details = instance.loadUserByUsername("username");
    assertEquals("username", details.getUsername());
    assertEquals("password", details.getPassword());
  }

  @Test
  public void testLoadUserByUsername_whenCalledWithNonExistUser_thenReturnAsExpected() throws UsernameNotFoundException {
    try {
      UserDetails details = instance.loadUserByUsername("not-username");
      fail();
    } catch (UsernameNotFoundException e) {
      assertThat(e.getMessage(), containsString("not-username"));
    }
  }

}
