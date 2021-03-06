package com.adidas.reviews.service.impl;

import com.adidas.reviews.service.TokenAuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

public class TokenAuthenticationServiceImpl  implements TokenAuthenticationService {
  private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationServiceImpl.class);

  static final long EXPIRATIONTIME = 864_000_000; // 10 days

  static final String SECRET = "ThisIsASecret";

  static final String TOKEN_PREFIX = "Bearer";

  static final String HEADER_STRING = "Authorization";

  public static void addAuthentication(HttpServletResponse res, String username) {
    logger.info("Add authentication to user with username => {}", username);
    String JWT = Jwts.builder().setSubject(username)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
  }

  public static Authentication getAuthentication(HttpServletRequest request) {
    logger.info("Get  authentication to user from request");

    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
      String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
          .getSubject();

      return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
    }
    return null;
  }
}
