package es.televoip.service.implement;

import es.televoip.exceptions.DataException;
import es.televoip.model.Person;
import es.televoip.model.dto.PersonDto;
import es.televoip.model.enums.SortFieldPerson;
import es.televoip.model.mapper.PersonMapper;
import es.televoip.repository.PersonRepository;
import es.televoip.service.PersonService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Slf4j // nos permite enviar texto a la consola mediante "log"
@Service
@Transactional
public class PersonServiceImpl implements PersonService {
// public class PersonServiceImpl extends BaseService<Person, Long, PersonDto> implements PersonService {

   private PersonRepository repository;

   private PersonMapper mapper;

   public PersonServiceImpl(PersonRepository repository, PersonMapper mapper) {
      this.mapper = mapper;
      this.repository = repository;
   }

   @Cacheable("cacheOnePerson")
   @Override
   public PersonDto getPersonById(Long id) {
      try {
         // obtenemos el objeto Optional del id
         Optional<Person> optionalPerson = repository.findById(id);
         if (optionalPerson.isPresent()) {
            return mapper.toDto(optionalPerson.get());
         } else {
            throw new DataException(HttpStatus.NOT_FOUND, "PERSON_ID_NOT_FOUND: " + id);
         }

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Cacheable("cacheManyPersons")
   @Override
   public List<PersonDto> getAllPersons() {
      try {
         List<Person> list = repository.findAll();
         return convertToDtoList(list);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Cacheable("cacheManyPersons")
   @Override
   public Page<PersonDto> getPersonsPaged(Pageable page) {
      try {
         Page<Person> pageE = repository.findAll(page);
         return pageE // se utiliza map directamente con 'taskPage' para convertirla en una página de DTO.
                .map(entity -> mapper.toDto(entity));

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Cacheable("cacheManyPersons")
   @Override
   public List<PersonDto> getPersonsSorted(SortFieldPerson sortBy, Sort.Direction sortOrder) {
      try {

         Sort sort = Sort.by(new Sort.Order(sortOrder, sortBy.getFieldName()));
         List<Person> list = repository.findAll(sort);
         return convertToDtoList(list);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Cacheable("cacheManyPersons")
   @Override
   public Page<PersonDto> getPersonsSortedAndPaged(SortFieldPerson sortBy, Sort.Direction sortOrder, Pageable pageable) {
      try {

         Sort sort = Sort.by(new Sort.Order(sortOrder, sortBy.getFieldName()));
         pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

         Page<Person> pageE = repository.findAll(pageable);
         return pageE // se utiliza map directamente para convertirla en una página de DTO.
                .map(entity -> mapper.toDto(entity));

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Cacheable("cacheManyPersons")
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

   @Cacheable("cacheManyPersons")
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

   @Cacheable("cacheOnePerson")
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

   @Cacheable("cacheOnePerson")
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

   @Cacheable("cacheOnePerson")
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

   @Caching(evict = {
      @CacheEvict(value = "PersonOneTask", allEntries = true),
      @CacheEvict(value = "PersonManyTasks", allEntries = true)})
   @Override
   public PersonDto savePerson(PersonDto personDto) {
      try {
         // Convertimos el objeto DTO a su entidad
         Person entity = mapper.toEntity(personDto);
         // Grabamos en la base de datos
         Person savedEntity = repository.save(entity);
         // devolvermos el objeto Dto
         return mapper.toDto(savedEntity);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Caching(evict = {
      @CacheEvict(value = "PersonOneTask", allEntries = true),
      @CacheEvict(value = "PersonManyTasks", allEntries = true)})
   @Override
   public List<PersonDto> saveAllPersons(List<PersonDto> personDtos) {
      try {

         if (personDtos.isEmpty()) {
            throw new DataException(HttpStatus.BAD_REQUEST, "LIST_IS_NULL");
         }

         List<Person> list = personDtos.stream() // convierte la lista dtos en un flujo (stream) de elementos
                .map(dto -> mapper.toEntity(dto)) // convierte cada elemento de la lista en un objeto 
                .collect(Collectors.toList()); // crea una lista de objetos

         List<Person> listE = repository.saveAll(list);
         return convertToDtoList(listE);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Caching(evict = {
      @CacheEvict(value = "PersonOneTask", allEntries = true),
      @CacheEvict(value = "PersonManyTasks", allEntries = true)})
   @Override
   public PersonDto updatePerson(Long id, PersonDto personDto) {
      try {
         repository.findById(id)
                .orElseThrow(() -> new DataException(HttpStatus.NOT_FOUND, "PERSON_ID_NOT_FOUND: " + id));

         Person entity = mapper.toEntity(personDto);
         entity.setId(id); // Asegurar que el ID se mantenga igual
         repository.save(entity);
         return mapper.toDto(entity);

      } catch (DataException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Caching(evict = {
      @CacheEvict(value = "PersonOneTask", allEntries = true),
      @CacheEvict(value = "PersonManyTasks", allEntries = true)})
   @Override
   public void deletePersonById(Long id) {
      try {
         repository.deleteById(id);
      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   //Método auxiliar para convertir una lista de entidades a DTOs
   private List<PersonDto> convertToDtoList(List<Person> persons) {
      return persons.stream() // se utiliza stream() y collect(Collectors.toList()) para convertirlo en una lista de DTO
             .map(entity -> mapper.toDto(entity))
             .collect(Collectors.toList());
   }

}
