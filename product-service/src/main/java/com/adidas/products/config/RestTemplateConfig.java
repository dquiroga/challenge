package com.adidas.products.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Configuration
public class RestTemplateConfig {

  private static final Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

  @Value("${api.http.default.timeout}")
  private Integer defaultTimeout;

  private HttpComponentsClientHttpRequestFactory requestFactory() throws Exception {
    HttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(RequestConfig.custom()
            .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
            .build())
        .build();
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
        httpClient);
    factory.setReadTimeout(defaultTimeout);
    factory.setConnectTimeout(defaultTimeout);
    return factory;
  }

  @Bean
  @Qualifier("challengeRestTemplate")
  @LoadBalanced
  public RestTemplate challengeRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  @Primary
  @Qualifier("defaultRestTemplate")
  public RestTemplate defaultRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    restTemplate.getMessageConverters()
        .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
    try {
      restTemplate.setRequestFactory(requestFactory());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return restTemplate;
  }

  @Bean
  @Qualifier("untrustedRestTemplate")
  public RestTemplate untrustedRestTemplate() {
    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

    SSLContext sslContext = null;
    try {
      sslContext = org.apache.http.ssl.SSLContexts.custom()
          .loadTrustMaterial(null, acceptingTrustStrategy)
          .build();

      SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

      CloseableHttpClient httpClient = HttpClients.custom()
          .setSSLSocketFactory(csf)
          .build();

      HttpComponentsClientHttpRequestFactory requestFactory =
          new HttpComponentsClientHttpRequestFactory();

      requestFactory.setHttpClient(httpClient);
      requestFactory.setReadTimeout(defaultTimeout);
      requestFactory.setConnectTimeout(defaultTimeout);

      RestTemplate restTemplate = new RestTemplate(requestFactory);
      restTemplate.getMessageConverters()
          .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
      return restTemplate;
    } catch (NoSuchAlgorithmException e) {
      logger.warn(e.getMessage());

    } catch (KeyManagementException e) {
      logger.warn(e.getMessage());
    } catch (KeyStoreException e) {
      logger.warn(e.getMessage());
    }

    return defaultRestTemplate();
  }


}