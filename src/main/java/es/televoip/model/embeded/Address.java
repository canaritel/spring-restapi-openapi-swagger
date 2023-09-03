package es.televoip.model.embeded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Address {  //No lo hacemos que extienda de Base al ser **Embebido**

   // Documento embebido, no requiero declarar id
   //
   @Column(nullable = false, updatable = true)
   @NotBlank(message = "Este campo es requerido") // NotBlank es ideal para los String
   @Size(min = 4, max = 200)
   private String street;

   @NotNull(message = "No puede estar vacío")
   @Pattern(regexp = "^[0-9]{5}$", message = "El Código Postal debe tener 5 dígitos numéricos")
   private int postalCode;

   @Column(nullable = false, updatable = true)
   @NotBlank(message = "Este campo es requerido") // NotBlank es ideal para los String
   @Size(min = 4, max = 50)
   private String city;

   @Column(nullable = false, updatable = true)
   @NotBlank(message = "Este campo es requerido") // NotBlank es ideal para los String
   @Size(min = 4, max = 50)
   private String country;

}
