package es.televoip.repository;

import es.televoip.model.Person;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {

   public List<Person> findByFirstNameContainingOrLastNameContainingOrEmailContainingOrDniContainingAllIgnoreCase(
          String name, String lastname, String email, String dni);

   public Page<Person> findByFirstNameContainingOrLastNameContainingOrEmailContainingOrDniContainingAllIgnoreCase(
          String name, String lastname, String email, String dni, Pageable page);

   public Long countByFirstNameContainingOrLastNameContainingOrEmailContainingOrDniContainingAllIgnoreCase(
          String name, String lastname, String email, String dni);

   public Optional<Person> findByDni(String dni);

   public Optional<Person> findByEmail(String email);

   public Optional<Person> findByUserAccessUserName(String username);

}
