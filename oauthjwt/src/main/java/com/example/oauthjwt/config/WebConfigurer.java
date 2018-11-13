package com.example.oauthjwt.config;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by hcj on 18-8-10
 */
@Configuration
@Slf4j
public class WebConfigurer {
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
//    config.addAllowedOrigin("*");
//    config.addAllowedHeader("Access-Control-Allow-Headers");
//    config.addAllowedHeader("x-requested-with,content-type");
//    config.addAllowedMethod("POST");
     config.addAllowedOrigin("*");
     config.addAllowedHeader("*");
      config.addAllowedMethod("*");

//    List<String> strings = Arrays.asList("Authorization","Link","X-Total-Count");
//    config.setExposedHeaders(strings);

//    if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
      log.debug("Registering CORS filter");
      source.registerCorsConfiguration("/**", config);
      source.registerCorsConfiguration("/api/**", config);
      source.registerCorsConfiguration("/management/**", config);
      source.registerCorsConfiguration("/v2/api-docs", config);
      source.registerCorsConfiguration("/*/api/**", config);
      source.registerCorsConfiguration("/*/management/**", config);
//    }
    return new CorsFilter(source);
  }
}
