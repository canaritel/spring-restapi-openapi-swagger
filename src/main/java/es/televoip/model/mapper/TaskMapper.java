package es.televoip.model.mapper;

import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaskMapper extends BaseMapper<Task, TaskDto> {

   @Override
   Task toEntity(TaskDto taskDto);

   @Override
   TaskDto toDto(Task task);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   @Override
   void updateEntity(TaskDto taskDto, @MappingTarget Task task);

}
