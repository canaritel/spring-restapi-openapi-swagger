package es.televoip.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.televoip.model.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // No se incluye en la respuesta JSON cuando su valor sea 'null'
public class TaskDto {

   // Usamos la especificación OPENAPI con Swagger3
   // Para más información: https://www.bezkoder.com/spring-boot-swagger-3/
   // https://www.baeldung.com/spring-swagger-hide-field
   //
   // Spring Boot Validation annotations:  https://www.bezkoder.com/spring-boot-validate-request-body/
   //
   @Schema(hidden = true)
   private Long id;

   @NotBlank(message = "Este campo es requerido") // NotBlank es ideal para los String
   @Size(min = 4, max = 100)
   private String title;

   @NotBlank(message = "Este campo es requerido")
   @Size(min = 5, max = 500)
   private String description;

   @NotNull(message = "No puede estar vacío")
   @Schema(accessMode = Schema.AccessMode.READ_ONLY) // solo visible en peticiones tipo GET
   @Builder.Default
   private TaskStatus taskStatus = TaskStatus.ON_TIME;

   @NotNull(message = "No puede estar vacío")
   @Schema(accessMode = Schema.AccessMode.READ_ONLY, defaultValue = "false") // Para la representación en Swagger
   @Builder.Default
   private Boolean isCompleted = Boolean.FALSE;

   @NotNull(message = "No puede estar vacío")
   @Min(value = 1, message = "La prioridad debe ser al menos 1")
   @Max(value = 9, message = "La prioridad debe ser como máximo 9")
   @Builder.Default
   @Schema(accessMode = Schema.AccessMode.WRITE_ONLY, defaultValue = "1") // Para la representación en Swagger
   private int priority = 1;

   @Null
   @Schema(accessMode = Schema.AccessMode.READ_ONLY)
   private LocalDateTime taskDateCreation;

   @Null
   @Builder.Default
   @Schema(accessMode = Schema.AccessMode.READ_ONLY, defaultValue = "null")
   private LocalDateTime taskDateFinished = null;

   @Null
   @Schema(hidden = true)
   private OffsetDateTime logDateCreated; // incluye información sobre la zona horaria y el desplazamiento con respecto a UTC

   @Null
   @Schema(hidden = true)
   private OffsetDateTime logLastUpdated; // incluye información sobre la zona horaria y el desplazamiento con respecto a UTC

}
