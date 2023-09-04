package es.televoip.model.mapper;

import es.televoip.model.Person;
import es.televoip.model.dto.PersonDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PersonMapper {

   Person toEntity(PersonDto personDto);

   PersonDto toDto(Person person);

   @Mapping(ignore = true, target = "logDateCreated")
   @Mapping(ignore = true, target = "logLastUpdated")
   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void updateEntity(PersonDto personDto, @MappingTarget Person person);

}
