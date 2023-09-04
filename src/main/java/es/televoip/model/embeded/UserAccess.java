package es.televoip.model.embeded;

import com.fasterxml.jackson.annotation.JsonIgnore;
import es.televoip.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data  // es equivalente a usar @ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstrutor al mismo tiempo
@NoArgsConstructor  // genera un constructor sin parámetros
@AllArgsConstructor  // genera un constructor con un parámetro para cada campo en su clase
@Builder  // se utiliza en clases, constructores y métodos para proporcionarle API de compilador complejas
public class UserAccess {

   // Documento embebido, no requiero declarar id
   //
   @Column(nullable = false, updatable = true)
   @NotBlank(message = "Este campo es requerido") // NotBlank es ideal para los String
   @Size(min = 4, max = 50)
   private String userName;

   @Column(nullable = false, updatable = true)
   @JsonIgnore
   @NotBlank(message = "Este campo es requerido") // NotBlank es ideal para los String
   @Size(min = 4, max = 100)
   private String hashedPassword;

   @Enumerated(EnumType.STRING)
   @Builder.Default
   private Role roles = Role.USER;

   @Column(nullable = false, updatable = true)
   private byte[] profilePicture;

}
