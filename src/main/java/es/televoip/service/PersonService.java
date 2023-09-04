package es.televoip.service;

import es.televoip.model.dto.PersonDto;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PersonService extends BaseService<PersonDto, Long> {

   List<PersonDto> search(String filter);

   List<PersonDto> searchPageable(String filter, Pageable page);

   PersonDto searchDni(String dni);

   List<PersonDto> searchFirstNameOrLastName(String filter);

   List<PersonDto> searchFirstNameOrLastName(String filter, Pageable page);

   Integer countByFirstNameOrLastName(String filter);

   Collection<PersonDto> findAllPageSort(Pageable page, Sort sort);

}
