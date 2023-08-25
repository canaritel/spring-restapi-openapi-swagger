package es.televoip.model.mapper;

import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaskMapper {

   @Mapping(target = "logDateCreated", ignore = true)
   @Mapping(target = "logLastUpdated", ignore = true)
   Task toEntity(TaskDto taskDto);

   TaskDto toDto(Task task);

   @Mapping(target = "logDateCreated", ignore = true)
   @Mapping(target = "logLastUpdated", ignore = true)
   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   void updateEntity(TaskDto taskDto, @MappingTarget Task task);

}
