package es.televoip.config;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan("es.televoip.model") // nuestro paquete para las entidades
@EnableJpaRepositories("es.televoip.repository") // nuestro paquete para los repositorios
@EnableTransactionManagement // habilita la gestión de transacciones 
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider") //  habilita la auditoría JPA y proporciona el Bean fecha y hora
public class DomainConfig {

   @Value("${app.auditTimezone}")
   private String auditTimezone; // Para auditar vamos a usar una zona horaria específicada en application.properties

   @Bean(name = "auditingDateTimeProvider")
   public DateTimeProvider dateTimeProvider() {
      ZoneId configuredZone = ZoneId.of(auditTimezone);
      return () -> Optional.of(OffsetDateTime.now(configuredZone));
   }

}
