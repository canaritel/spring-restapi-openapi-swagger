package es.televoip.service;

import es.televoip.model.dto.PersonDto;
import es.televoip.model.enums.SortFieldPerson;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PersonService {

   PersonDto getPersonById(Long id);

   List<PersonDto> getAllPersons();

   Page<PersonDto> getPersonsPaged(Pageable page);

   List<PersonDto> getPersonsSorted(SortFieldPerson sortBy, Sort.Direction sortOrder);

   Page<PersonDto> getPersonsSortedAndPaged(SortFieldPerson sortBy, Sort.Direction sortOrder, Pageable pageable);

   List<PersonDto> getPersonsByFilter(String filter);

   Page<PersonDto> getPersonsByFilterPageable(String filter, Pageable page);

   PersonDto getPersonByDni(String dni);

   PersonDto getPersonByEmail(String email);

   PersonDto getPersonByUserAccessUserName(String username);

   PersonDto savePerson(@Valid PersonDto personDto);

   List<PersonDto> saveAllPersons(@Valid List<PersonDto> personDtos);

   PersonDto updatePerson(Long id, @Valid PersonDto personDto);

   void deletePersonById(Long id);

}
