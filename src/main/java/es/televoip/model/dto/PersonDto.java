package es.televoip.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.televoip.model.embeded.Address;
import es.televoip.model.embeded.UserAccess;
import es.televoip.model.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // No se incluye en la respuesta JSON cuando su valor sea 'null'
public class PersonDto {

   // Usamos la especificación OPENAPI con Swagger3
   // Para más información: https://www.bezkoder.com/spring-boot-swagger-3/
   // https://www.baeldung.com/spring-swagger-hide-field
   //
   // Spring Boot Validation annotations:  https://www.bezkoder.com/spring-boot-validate-request-body/
   //
   @Schema(hidden = true)
   private Long id;

   @NotBlank(message = "El nombre es requerido")
   @Size(min = 3, max = 50)
   private String firstName;

   @NotBlank(message = "El apellido es requerido")
   @Size(min = 4, max = 100)
   private String lastName;

   @Pattern(regexp = "[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]", message = "El DNI debe tener 8 dígitos y una letra")
   @NotBlank(message = "El DNI es requerido")
   private String dni;

   @NotBlank(message = "El correo electrónico es requerido")
   @Email(message = "El correo electrónico no es válido")
   @Size(min = 5, max = 50)
   private String email;

   @Null
   private Gender gender;

   @Null
   private String phone;

   @Null
   private LocalDate dateOfBirth;

   @NotNull
   private boolean important;

   @Null
   private UserAccess userAccess;

   @Null
   private Address address;

   @Null
   private List<TaskDto> tasks;

}
