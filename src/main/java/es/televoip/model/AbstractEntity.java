package es.televoip.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@MappedSuperclass // no se mapean directamente en la base de datos, pero sus propiedades se heredan por las clases derivadas
@Data // es equivalente a usar @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstrutor al mismo tiempo
@NoArgsConstructor  // genera un constructor sin parámetros
@AllArgsConstructor  // genera un constructor con un parámetro para cada campo en su clase
public abstract class AbstractEntity implements Serializable {

   private static final long serialVersionUID = -7643472199261145774L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @CreatedDate // Para auditoria. Usamos la zona horaria Spain/Madrid -> mirar clase DomainConfig
   @Column(name = "date_created", nullable = true, updatable = false)
   private OffsetDateTime logDateCreated; // incluye información sobre la zona horaria y el desplazamiento con respecto a UTC

   @LastModifiedDate // Para auditoria. Usamos la zona horaria Spain/Madrid -> mirar clase DomainConfig
   @Column(name = "last_updated", nullable = true, updatable = true)
   private OffsetDateTime logLastUpdated; // incluye información sobre la zona horaria y el desplazamiento con respecto a UTC

}
