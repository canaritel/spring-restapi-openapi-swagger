package es.televoip.service.implement;

import es.televoip.constant.TaskConstant;
import es.televoip.exceptions.DataException;
import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskStatus;
import es.televoip.model.mapper.TaskMapper;
import es.televoip.repository.TaskRepository;
import es.televoip.service.BaseService;
import es.televoip.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Validated
@Slf4j // nos permite enviar texto a la consola mediante "log"
@Service
@Transactional
public class TaskServiceImpl extends BaseService<Task, Long, TaskDto> implements TaskService {

   private TaskRepository repository;

   public TaskServiceImpl(TaskRepository repository, TaskMapper mapper) {
      super(repository, mapper);
      this.repository = repository;
   }

   @Override
   public List<TaskDto> getTasksByFilter(String filter) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Page<TaskDto> getTasksByFilterPageable(String filter, Pageable page) {

      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

   }

   @Override
   public List<TaskDto> getTasksByTitleContaining(String title) {
      try {

         List<Task> tasks = repository.getAllByTitleContainingIgnoreCase(title);
         return convertToDtoList(tasks);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Override
   public List<TaskDto> getTasksByTaskStatus(TaskStatus status) {

      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

   }

   @Override
   public List<TaskDto> getTasksByCompletion(boolean isCompleted) {

      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody

   }

   @Override
   public TaskDto saveTaskAndCheckCreationDate(TaskDto taskDto) {
      try {

         Task task = mapper.toEntity(taskDto);
         if (task.getTaskDateCreation() == null) {
            task.setTaskDateCreation(LocalDateTime.now());
         }
         Task createdTask = repository.save(task);
         return mapper.toDto(createdTask);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Override
   public List<TaskDto> saveAllTasks(List<TaskDto> taskDtos) {
      try {

         if (taskDtos.isEmpty()) {
            throw new DataException(HttpStatus.BAD_REQUEST, TaskConstant.TASK_IS_NULL);
         }
         List<Task> tasks = taskDtos.stream() // convierte la lista taskDtos en un flujo (stream) de elementos
                .map(mapper::toEntity) // convierte cada elemento de la lista taskDtos en un objeto Task
                .peek(action -> {
                   /* permite realizar una acción en cada elemento del flujo sin cambiar los elementos mismos  */
                   if (action.getTaskDateCreation() == null) {
                      action.setTaskDateCreation(LocalDateTime.now());
                   }
                })
                .collect(Collectors.toList()); // crea una lista de objetos Task

         List<Task> createdTasks = repository.saveAll(tasks);
         return convertToDtoList(createdTasks);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Override
   public TaskDto updateTaskDateOfFinished(Long id, LocalDateTime newDateOfFinished) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public TaskDto updateTaskStatus(Long id, TaskStatus taskStatus) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public TaskDto updateTaskIsCompleted(Long id, Boolean isCompleted) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   @Override
   public TaskDto markTaskAsCompleted(Long id) {
      throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
   }

   //    Método auxiliar para convertir una lista de entidades a DTOs
   private List<TaskDto> convertToDtoList(List<Task> tasks) {
      return tasks.stream() // se utiliza stream() y collect(Collectors.toList()) para convertirlo en una lista de DTO
             .map(mapper::toDto)
             .collect(Collectors.toList());
   }

}
