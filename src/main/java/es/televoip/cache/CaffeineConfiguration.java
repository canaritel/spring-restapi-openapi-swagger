package es.televoip.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
@Profile("!test") // Esta configuración solo se aplicará si el perfil no es "test"
public class CaffeineConfiguration {
   // Need to define caffeine bean first with caching behavior, expiration, cache limit, etc. 
// https://blog.coditas.com/coders/caching-in-spring-with-caffeine/ 

   @Value("${cache.enabled}")
   private boolean cacheEnabled;

   @SuppressWarnings("rawtypes")
   @Bean
   public Caffeine caffeineConfig() {
      return Caffeine.newBuilder()
             .maximumSize(200) // máximo de elementos
             .expireAfterWrite(60, TimeUnit.MINUTES); // un TTL (tiempo de vida) de 60 minutos
   }

// We need to create one more bean using the Spring CacheManager interface, Caffeine provides its implementation of this interface.
   @SuppressWarnings({"rawtypes", "unchecked"})
   @Bean
   public CacheManager cacheManager(Caffeine caffeine) {
      CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager(
             "cacheOneTask", "cacheManyTasks",
             "cacheOnePerson", "cacheManyPersons");

      // Para no activamos la caché en los TEST
      if (cacheEnabled) {
         caffeineCacheManager.setCaffeine(caffeine);
      }

      return caffeineCacheManager;
   }

}
