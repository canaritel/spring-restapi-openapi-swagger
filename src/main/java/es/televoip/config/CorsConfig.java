package es.televoip.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/*
// Lo aplicamos directamente en application.properties !!!
//
@Configuration
public class CorsConfig {

   @Bean
   public CorsFilter corsFilter() {
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
      config.setAllowedMethods(Arrays.asList("GET", "PUT", "DELETE"));
      config.setAllowedHeaders(Collections.singletonList("*"));
      config.setAllowCredentials(true);

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);

      return new CorsFilter(source);
   }

}
 */
