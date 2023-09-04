package es.televoip.repository;

import es.televoip.model.Person;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends BaseRepository<Person, Long> {

   List<Person> findByFirstNameContainingOrLastNameContainingAllIgnoreCase(String name, String lastname);

   Page<Person> findByFirstNameContainingOrLastNameContainingAllIgnoreCase(String name, String lastname, Pageable page);

   Long countByFirstNameContainingOrLastNameContainingAllIgnoreCase(String name, String lastname);

}
