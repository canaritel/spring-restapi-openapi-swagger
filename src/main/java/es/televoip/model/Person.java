package es.televoip.model;

import es.televoip.model.embeded.UserAccess;
import es.televoip.model.embeded.Address;
import es.televoip.model.enums.Gender;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person extends BaseEntity {

   @Column(nullable = false, updatable = true)
   private String firstName;

   @Column(nullable = false, updatable = true)
   private String lastName;

   @Column(unique = true, nullable = false, updatable = true)
   private String dni;

   @Column(unique = true, nullable = false, updatable = true)
   private String email;

   @Enumerated(EnumType.STRING)
   private Gender gender;

   @Column(nullable = false, updatable = true)
   private String phone;

   @Column(nullable = false, updatable = true)
   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
   private LocalDate dateOfBirth;

   @Column(nullable = false, updatable = true)
   @Builder.Default
   private boolean important = false;

   @Embedded
   private UserAccess userAccess; // es **Embebido**

   @Embedded
   private Address address; // es **Embebido**

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   @JoinColumn(name = "person_id") // Nombre de la columna en la tabla Task que hace referencia a Person
   private List<Task> tasks;

}
