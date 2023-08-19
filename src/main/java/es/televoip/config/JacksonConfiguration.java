package es.televoip.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
// Lo aplicamos directamente en application.properties !!!
@Configuration
public class JacksonConfiguration {

   @Bean
   public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
      return jacksonObjectMapperBuilder -> {
         jacksonObjectMapperBuilder.featuresToDisable(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                DeserializationFeature.ACCEPT_FLOAT_AS_INT,
                SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      };
   }

}
*/