package es.televoip.service;

import es.televoip.model.dto.PersonDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {

   List<PersonDto> getPersonsByFilter(String filter);

   Page<PersonDto> getPersonsByFilterPageable(String filter, Pageable page);

   PersonDto getPersonByDni(String dni);

   List<PersonDto> getPersonsByFirstNameOrLastName(String filter);

   Page<PersonDto> getPersonsByFirstNameOrLastNamePageable(String filter, Pageable page);

}
