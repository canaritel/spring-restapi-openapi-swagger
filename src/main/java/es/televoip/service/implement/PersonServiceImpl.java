package es.televoip.service.implement;

import es.televoip.exceptions.DataException;
import es.televoip.model.Person;
import es.televoip.model.dto.PersonDto;
import es.televoip.model.mapper.PersonMapper;
import es.televoip.repository.PersonRepository;
import es.televoip.service.BaseService;
import es.televoip.service.PersonService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Slf4j // nos permite enviar texto a la consola mediante "log"
@Service
@Transactional
public class PersonServiceImpl extends BaseService<Person, Long, PersonDto> implements PersonService {

   private PersonRepository repository;

   public PersonServiceImpl(PersonRepository repository, PersonMapper mapper) {
      super(repository, mapper);
      this.repository = repository;
   }

   @Override
   public List<PersonDto> getPersonsByFilter(String filter) {
      try {
         // Realizamos una consulta a la base de datos para obtener las tareas que coincidan con el filtro
         List<Person> persons = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingOrDniContainingAllIgnoreCase(
                filter, filter, filter, filter);

         // Convertimos las entidades a DTOs
         return convertToDtoList(persons);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Override
   public Page<PersonDto> getPersonsByFilterPageable(String filter, Pageable page) {
      try {
         // Realizamos una consulta a la base de datos para obtener las tareas que coincidan con el filtro
         Page<Person> persons = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingOrDniContainingAllIgnoreCase(
                filter, filter, filter, filter, page);

         // Convertimos las entidades a DTOs
         return persons.map(entity -> mapper.toDto(entity));

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }
   
   @Override
   public PersonDto getPersonByDni(String dni) {
      try {
         // obtenemos el objeto Optional
         Optional<Person> person = repository.findByDni(dni);
         if (person.isPresent()) {
            return mapper.toDto(person.get());
         } else {
            throw new DataException(HttpStatus.NOT_FOUND, "DNI_NOT_FOUND: " + dni);
         }

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }
  
@Override
public PersonDto getPersonByEmail(String email) {
	try {
      // obtenemos el objeto Optional
      Optional<Person> person = repository.findByEmail(email);
      if (person.isPresent()) {
         return mapper.toDto(person.get());
      } else {
         throw new DataException(HttpStatus.NOT_FOUND, "EMAIL_NOT_FOUND: " + email);
      }

   } catch (DataException ex) {
      throw ex;
   } catch (Exception ex) {
      throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
   }
}

@Override
public PersonDto getPersonByUserAccessUserName(String username) {
	try {
      // obtenemos el objeto Optional
      Optional<Person> person = repository.findByUserAccessUserName(username);
      if (person.isPresent()) {
         return mapper.toDto(person.get());
      } else {
         throw new DataException(HttpStatus.NOT_FOUND, "USERNAME_NOT_FOUND: " + username);
      }

   } catch (DataException ex) {
      throw ex;
   } catch (Exception ex) {
      throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
   }
}

//Método auxiliar para convertir una lista de entidades a DTOs
private List<PersonDto> convertToDtoList(List<Person> persons) {
return persons.stream() // se utiliza stream() y collect(Collectors.toList()) para convertirlo en una lista de DTO
       .map(mapper::toDto)
       .collect(Collectors.toList());
}

}
