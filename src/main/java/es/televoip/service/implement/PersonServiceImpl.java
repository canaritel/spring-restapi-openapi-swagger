package es.televoip.service.implement;

import es.televoip.model.Person;
import es.televoip.model.dto.PersonDto;
import es.televoip.model.mapper.BaseMapper;
import es.televoip.model.mapper.PersonMapper;
import es.televoip.repository.PersonRepository;
import es.televoip.service.BaseService;
import es.televoip.service.PersonService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Slf4j // nos permite enviar texto a la consola mediante "log"
@Service
@Transactional
public class PersonServiceImpl extends BaseService<Person, Long, PersonDto> implements PersonService {

   public PersonServiceImpl(PersonRepository repository, PersonMapper mapper) {
      super(repository, (BaseMapper<Person, PersonDto>) mapper);
   }

   @Override
   public List<PersonDto> getPersonsByFilter(String filter) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public Page<PersonDto> getPersonsByFilterPageable(String filter, Pageable page) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public PersonDto getPersonByDni(String dni) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public List<PersonDto> getPersonsByFirstNameOrLastName(String filter) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public Page<PersonDto> getPersonsByFirstNameOrLastNamePageable(String filter, Pageable page) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }



}
