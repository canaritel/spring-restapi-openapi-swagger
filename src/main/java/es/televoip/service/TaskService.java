package es.televoip.service;

import es.televoip.constant.TaskConstant;
import es.televoip.exceptions.TaskException;
import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskSortField;
import es.televoip.model.enums.TaskStatus;
import es.televoip.model.mapper.TaskMapper;
import es.televoip.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

   private final TaskRepository repository;
   private final TaskMapper mapper;

   public TaskService(TaskRepository repository, TaskMapper mapper) {
      this.repository = repository;
      this.mapper = mapper;
   }

   @Transactional(readOnly = true)
   public TaskDto getTask(Long id) {
      try {

         Optional<Task> optionalTask = repository.findById(id);
         if (optionalTask.isPresent()) {
            return mapper.toDto(optionalTask.get());
         } else {
            throw new TaskException(HttpStatus.NOT_FOUND, TaskConstant.TASK_NOT_FOUND + id);
         }

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional(readOnly = true)
   public List<TaskDto> getAllTask() {
      try {

         List<Task> tasks = repository.findAll();
         return convertToDtoList(tasks);

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional(readOnly = true)
   public List<TaskDto> getAllTasksSorted(TaskSortField sortBy, Sort.Direction sortOrder) {
      try {

         Sort sort = Sort.by(new Sort.Order(sortOrder, sortBy.getFieldName()));
         List<Task> tasks = repository.findAll(sort);
         return convertToDtoList(tasks);

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional(readOnly = true)
   public Page<TaskDto> getAllTasksSortedAndPaginated(TaskSortField sortBy, Sort.Direction sortOrder, int page, int size) {
      try {

         Sort sort = Sort.by(new Sort.Order(sortOrder, sortBy.getFieldName()));
         Pageable pageable = PageRequest.of(page, size, sort);
         Page<Task> taskPage = repository.findAll(pageable);
         return taskPage // se utiliza map directamente con 'taskPage' para convertirla en una página de DTO.
                .map(mapper::toDto);

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional(readOnly = true)
   public List<TaskDto> getAllTasksByTitleContaining(String title) {
      try {

         List<Task> tasks = repository.getAllByTitleContainingIgnoreCase(title);
         return convertToDtoList(tasks);

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional(readOnly = true)
   public List<TaskDto> getAllByTaskStatus(TaskStatus status) {
      try {

         List<Task> tasks = repository.findAllByTaskStatus(status);
         return convertToDtoList(tasks);

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional(readOnly = true)
   public List<TaskDto> getAllTasksByCompletionStatus(boolean isCompleted) {
      try {

         List<Task> tasks;

         if (isCompleted) {
            tasks = repository.findByIsCompletedTrue();
         } else {
            tasks = repository.findByIsCompletedFalse();
         }

         return convertToDtoList(tasks);

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional
   public TaskDto createTask(TaskDto taskDto) {
      try {

         Task task = mapper.toEntity(taskDto);
         if (task.getTaskDateCreation() == null) {
            task.setTaskDateCreation(LocalDateTime.now());
         }
         Task createdTask = repository.save(task);
         return mapper.toDto(createdTask);

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional
   public List<TaskDto> createAllTasks(List<TaskDto> taskDtos) {
      try {

         if (taskDtos.isEmpty()) {
            throw new TaskException(HttpStatus.BAD_REQUEST, TaskConstant.TASK_IS_NULL);
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

      } catch (TaskException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional
   public TaskDto updateTask(Long id, TaskDto taskDto) {
      try {

         repository.findById(id)
                .orElseThrow(() -> new TaskException(HttpStatus.NOT_FOUND, TaskConstant.TASK_NOT_FOUND + id));

         Task updatedTask = mapper.toEntity(taskDto);
         updatedTask.setId(id); // Asegurar que el ID se mantenga igual
         repository.save(updatedTask);
         return mapper.toDto(updatedTask);

      } catch (TaskException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @SuppressWarnings("null")
   @Transactional
   public TaskDto updateTaskDateOfFinished(Long id, LocalDateTime newDateOfFinished) {
      try {

         Task existingTask = repository.findById(id)
                .orElseThrow(() -> new TaskException(HttpStatus.NOT_FOUND, TaskConstant.TASK_NOT_FOUND + id));

         //  Ejecutar validaciones personalizadas. ¡OJO! si funciona cuando querramos realizar TaskServiceTest
         if (existingTask.getTaskDateCreation() != null && newDateOfFinished.isBefore(existingTask.getTaskDateCreation())) {
            throw new TaskException(HttpStatus.BAD_REQUEST, TaskConstant.TASK_DATE_FAIL);
         }

         existingTask.setTaskDateFinished(newDateOfFinished);
         Task updatedTask = repository.save(existingTask);
         return mapper.toDto(updatedTask);

      } catch (TaskException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @SuppressWarnings("null")
   @Transactional
   public TaskDto updateTaskStatus(Long id, TaskStatus taskStatus) {
      try {

         Task existingTask = repository.findById(id)
                .orElseThrow(() -> new TaskException(HttpStatus.NOT_FOUND, TaskConstant.TASK_NOT_FOUND + id));

         existingTask.setTaskStatus(taskStatus);
         Task updatedTask = repository.save(existingTask);
         return mapper.toDto(updatedTask);

      } catch (TaskException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @SuppressWarnings("null")
   @Transactional
   public TaskDto updateTaskIsCompleted(Long id, Boolean isCompleted) {
      try {

         Task existingTask = repository.findById(id)
                .orElseThrow(() -> new TaskException(HttpStatus.NOT_FOUND, TaskConstant.TASK_NOT_FOUND + id));

         existingTask.setIsCompleted(isCompleted);
         Task updatedTask = repository.save(existingTask);
         return mapper.toDto(updatedTask);

      } catch (TaskException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional
   public TaskDto updateTaskToCompleted(Long id) {
      try {

         Task existingTask = repository.findById(id)
                .orElseThrow(() -> new TaskException(HttpStatus.NOT_FOUND, TaskConstant.TASK_NOT_FOUND + id));

         repository.markTaskAsCompleted(id);
         existingTask.setIsCompleted(Boolean.TRUE);
         return mapper.toDto(existingTask);

      } catch (TaskException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @Transactional
   public void deleteTask(Long id) {
      try {

         repository.findById(id)
                .orElseThrow(() -> new TaskException(HttpStatus.NOT_FOUND, TaskConstant.TASK_NOT_FOUND + id));
         repository.deleteById(id);

      } catch (TaskException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new TaskException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   // Método auxiliar para convertir una lista de entidades a DTOs
   private List<TaskDto> convertToDtoList(List<Task> tasks) {
      return tasks.stream() // se utiliza stream() y collect(Collectors.toList()) para convertirlo en una lista de DTO
             .map(mapper::toDto)
             .collect(Collectors.toList());
   }

}
