package es.televoip.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.televoip.model.enums.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
* * *  Este objeto se urará exclusivamente en los métodos tipo POST  * * *
 */
/*
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskWriteDto {

   //@JsonIgnore // Este campo no será visible en la representación JSON pública de esta entidad
   @Schema(hidden = true, accessMode = AccessMode.READ_ONLY)
   private Long id;

   @NotNull(message = "No puede estar vacío")
   @NotBlank(message = "Este campo es requerido")
   @Size(min = 4, max = 100)
   private String title;

   @NotNull(message = "No puede estar vacío")
   @NotBlank(message = "Este campo es requerido")
   @Size(min = 5, max = 500)
   private String description;

   @JsonIgnore // Este campo no será visible en la representación JSON pública de esta entidad
   @NotNull(message = "No puede estar vacío")
   @Schema(defaultValue = "false") // Para la representación en Swagger
   @Builder.Default
   private Boolean isCompleted = Boolean.FALSE;

   @JsonIgnore // Este campo no será visible en la representación JSON pública de esta entidad
   @Null
   @Builder.Default
   private TaskStatus taskStatus = TaskStatus.ON_TIME;

   @JsonIgnore // Este campo no será visible en la representación JSON pública de esta entidad
   @Null
   @Builder.Default
   @Schema(accessMode = AccessMode.READ_ONLY, defaultValue = "null")
   private LocalDateTime taskDateCreation = null;

   @JsonIgnore // Este campo no será visible en la representación JSON pública de esta entidad
   @Null
   @Builder.Default
   @Schema(accessMode = AccessMode.READ_ONLY, defaultValue = "null")
   private LocalDateTime taskDateFinished = null;

}
*/