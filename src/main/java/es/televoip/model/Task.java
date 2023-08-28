package es.televoip.model;

import es.televoip.model.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task extends AbstractEntity {

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

   @Column(nullable = true, updatable = true)
   private LocalDateTime taskDateFinished; // la zona horaria la coge del fichero application.properties

}
