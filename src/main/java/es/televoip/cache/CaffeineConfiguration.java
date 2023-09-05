package es.televoip.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CaffeineConfiguration {

// Need to define caffeine bean first with caching behavior, expiration, cache limit, etc. 
// https://blog.coditas.com/coders/caching-in-spring-with-caffeine/ 
   @Bean
   public Caffeine caffeineConfig() {
      return Caffeine.newBuilder()
             .maximumSize(100) // máximo de 100 elementos
             .expireAfterWrite(60, TimeUnit.MINUTES); // un TTL (tiempo de vida) de 60 minutos
   }

// We need to create one more bean using the Spring CacheManager interface, Caffeine provides its implementation of this interface.
   @Bean
   public CacheManager cacheManager(Caffeine caffeine) {
      CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(
             "cacheTask", "cacheTasks",
             "cachePerson", "cachePersons");
      caffeineCacheManager.setCaffeine(caffeine);

      return caffeineCacheManager;
   }

}
