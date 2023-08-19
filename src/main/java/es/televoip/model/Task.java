package es.televoip.model;

import es.televoip.model.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String title;

   @Column(nullable = false)
   private String description;

   @Column(nullable = false, updatable = true)
   private TaskStatus taskStatus;  // podrá ser ON_TIME | LATE

   @Column(nullable = false, updatable = true)
   private Boolean isCompleted;

   @Column(nullable = false, updatable = true)
   private int priority; // del 1 (mínima) al 9 (máxima) 

   @Column(nullable = true, updatable = false)
   private LocalDateTime taskDateCreation; // la zona horaria la coge del fichero application.properties

   @FutureOrPresent(message = "La fecha debe ser igual o mayor a la fecha actual")
   @Column(nullable = true, updatable = true)
   private LocalDateTime taskDateFinished; // la zona horaria la coge del fichero application.properties

   @CreatedDate // Para auditoria. Usamos la zona horaria Spain/Madrid -> mirar clase DomainConfig
   @Column(nullable = false, updatable = false)
   private OffsetDateTime logDateCreated; // incluye información sobre la zona horaria y el desplazamiento con respecto a UTC

   @LastModifiedDate // Para auditoria. Usamos la zona horaria Spain/Madrid -> mirar clase DomainConfig
   @Column(nullable = false, updatable = true)
   private OffsetDateTime logLastUpdated; // incluye información sobre la zona horaria y el desplazamiento con respecto a UTC

}
