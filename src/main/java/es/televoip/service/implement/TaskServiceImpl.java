package es.televoip.service.implement;

import es.televoip.aspects.LogExecutionTime;
import es.televoip.constant.TaskConstant;
import es.televoip.exceptions.DataException;
import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.SortFieldTask;
import es.televoip.model.enums.TaskStatus;
import es.televoip.model.mapper.TaskMapper;
import es.televoip.repository.TaskRepository;
import es.televoip.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.stream.Collectors;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Validated
@Slf4j // nos permite enviar texto a la consola mediante "log"
@Service
@Transactional
public class TaskServiceImpl implements TaskService {
// public class TaskServiceImpl extends BaseService<Task, Long, TaskDto> implements TaskService {

   private TaskRepository repository;

   private TaskMapper mapper;

   public TaskServiceImpl(TaskRepository repository, TaskMapper mapper) {
      this.mapper = mapper;
      this.repository = repository;
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheOneTask")
   @Override
   public TaskDto getTaskById(Long id) {
      try {
         // obtenemos el objeto Optional del id
         Optional<Task> optionalTask = repository.findById(id);
         if (optionalTask.isPresent()) {
            return mapper.toDto(optionalTask.get());
         } else {
            throw new DataException(HttpStatus.NOT_FOUND, "TASK_ID_NOT_FOUND: " + id);
         }

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public List<TaskDto> getAllTasks() {
      try {
         List<Task> list = repository.findAll();
         return convertToDtoList(list);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public Page<TaskDto> getTasksPaged(Pageable page) {
      try {
         Page<Task> pageE = repository.findAll(page);
         return pageE // se utiliza map directamente con 'taskPage' para convertirla en una página de DTO.
                .map(entity -> mapper.toDto(entity));

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public List<TaskDto> getTasksSorted(SortFieldTask sortBy, Sort.Direction sortOrder) {
      try {

         Sort sort = Sort.by(new Sort.Order(sortOrder, sortBy.getFieldName()));
         List<Task> list = repository.findAll(sort);
         return convertToDtoList(list);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public Page<TaskDto> getTasksSortedAndPaged(SortFieldTask sortBy, Sort.Direction sortOrder, Pageable pageable) {
      try {

         Sort sort = Sort.by(new Sort.Order(sortOrder, sortBy.getFieldName()));
         pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

         Page<Task> pageE = repository.findAll(pageable);
         return pageE // se utiliza map directamente para convertirla en una página de DTO.
                .map(entity -> mapper.toDto(entity));

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public List<TaskDto> getTasksByFilter(String filter) {
      try {
         // Realizamos una consulta a la base de datos para obtener las tareas que coincidan con el filtro
         List<Task> tasks = repository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(
                filter, filter);

         // Convertimos las entidades a DTOs
         return convertToDtoList(tasks);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public Page<TaskDto> getTasksByFilterPageable(String filter, Pageable page) {
      try {
         // Realizamos una consulta a la base de datos para obtener las tareas que coincidan con el filtro
         Page<Task> tasks = repository.findByTitleContainingOrDescriptionContainingAllIgnoreCase(
                filter, filter, page);

         // Convertimos las entidades a DTOs
         return tasks.map(entity -> mapper.toDto(entity));

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public List<TaskDto> getTasksByTitleContaining(String title) {
      try {

         List<Task> tasks = repository.findAllByTitleContainingIgnoreCase(title);
         return convertToDtoList(tasks);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public List<TaskDto> getTasksByTaskStatus(TaskStatus status) {
      try {

         List<Task> tasks = repository.findAllByTaskStatus(status);
         return convertToDtoList(tasks);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Cacheable("cacheManyTasks")
   @Override
   public List<TaskDto> getTasksByCompletion(boolean isCompleted) {
      try {

         List<Task> tasks;
         if (isCompleted) {
            tasks = repository.findByIsCompletedTrue();
         } else {
            tasks = repository.findByIsCompletedFalse();
         }

         return convertToDtoList(tasks);

      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Caching(evict = {
      @CacheEvict(value = "cacheOneTask", allEntries = true),
      @CacheEvict(value = "cacheManyTasks", allEntries = true)})
   @Override
   public TaskDto saveTask(TaskDto taskDto) {
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

   @LogExecutionTime // medimos el tiempo de ejecución
   @Caching(evict = {
      @CacheEvict(value = "cacheOneTask", allEntries = true),
      @CacheEvict(value = "cacheManyTasks", allEntries = true)})
   @Override
   public List<TaskDto> saveAllTasks(List<TaskDto> taskDtos) {
      try {

         if (taskDtos.isEmpty()) {
            throw new DataException(HttpStatus.BAD_REQUEST, TaskConstant.TASK_IS_NULL);
         }
         List<Task> tasks = taskDtos.stream() // convierte la lista taskDtos en un flujo (stream) de elementos
                .map(mapper::toEntity) // convierte cada elemento de la lista taskDtos en un objeto Task
                .peek(action -> { //permite realizar una acción en cada elemento del flujo sin cambiar los elementos mismos
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

   @LogExecutionTime // medimos el tiempo de ejecución
   @Caching(evict = {
      @CacheEvict(value = "cacheOneTask", allEntries = true),
      @CacheEvict(value = "cacheManyTasks", allEntries = true)})
   @Override
   public TaskDto updateTask(Long id, TaskDto taskDto) {
      try {
         repository.findById(id)
                .orElseThrow(() -> new DataException(HttpStatus.NOT_FOUND, "TASK_ID_NOT_FOUND: " + id));

         Task entity = mapper.toEntity(taskDto);
         entity.setId(id); // Asegurar que el ID se mantenga igual
         repository.save(entity);
         return mapper.toDto(entity);

      } catch (DataException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Caching(evict = {
      @CacheEvict(value = "cacheOneTask", allEntries = true),
      @CacheEvict(value = "cacheManyTasks", allEntries = true)})
   @Override
   public TaskDto updateTaskDateOfFinished(Long id, LocalDateTime newDateOfFinished) {
      try {

         Task existingTask = repository.findById(id)
                .orElseThrow(() -> new DataException(HttpStatus.NOT_FOUND, TaskConstant.TASK_ID_NOT_FOUND + id));

         //  Ejecutar validaciones personalizadas. ¡OJO! si funciona cuando querramos realizar TaskServiceTest
         if (existingTask.getTaskDateCreation() != null && newDateOfFinished.isBefore(existingTask.getTaskDateCreation())) {
            throw new DataException(HttpStatus.BAD_REQUEST, TaskConstant.TASK_DATE_FAIL);
         }

         existingTask.setTaskDateFinished(newDateOfFinished);
         Task updatedTask = repository.save(existingTask);
         return mapper.toDto(updatedTask);

      } catch (DataException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Caching(evict = {
      @CacheEvict(value = "cacheOneTask", allEntries = true),
      @CacheEvict(value = "cacheManyTasks", allEntries = true)})
   @Override
   public TaskDto updateTaskStatus(Long id, TaskStatus taskStatus) {
      try {

         Task existingTask = repository.findById(id)
                .orElseThrow(() -> new DataException(HttpStatus.NOT_FOUND, TaskConstant.TASK_ID_NOT_FOUND + id));

         existingTask.setTaskStatus(taskStatus);
         Task updatedTask = repository.save(existingTask);
         return mapper.toDto(updatedTask);

      } catch (DataException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Caching(evict = {
      @CacheEvict(value = "cacheOneTask", allEntries = true),
      @CacheEvict(value = "cacheManyTasks", allEntries = true)})
   @Override
   public TaskDto updateTaskIsCompleted(Long id, Boolean isCompleted) {
      try {

         Task existingTask = repository.findById(id)
                .orElseThrow(() -> new DataException(HttpStatus.NOT_FOUND, TaskConstant.TASK_ID_NOT_FOUND + id));

         existingTask.setIsCompleted(isCompleted);
         Task updatedTask = repository.save(existingTask);
         return mapper.toDto(updatedTask);

      } catch (DataException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Caching(evict = {
      @CacheEvict(value = "cacheOneTask", allEntries = true),
      @CacheEvict(value = "cacheManyTasks", allEntries = true)})
   @Override
   public TaskDto markTaskAsCompleted(Long id) {
      try {

         Task existingTask = repository.findById(id)
                .orElseThrow(() -> new DataException(HttpStatus.NOT_FOUND, TaskConstant.TASK_ID_NOT_FOUND + id));

         repository.markTaskAsCompleted(id);
         existingTask.setIsCompleted(Boolean.TRUE);
         return mapper.toDto(existingTask);

      } catch (DataException ex) { // Lo pongo antes del Exception final para poder capturar mis excepciones
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   @LogExecutionTime // medimos el tiempo de ejecución
   @Caching(evict = {
      @CacheEvict(value = "cacheOneTask", allEntries = true),
      @CacheEvict(value = "cacheManyTasks", allEntries = true)})
   @Override
   public void deleteTaskById(Long id) {
      try {
         repository.deleteById(id);
      } catch (DataException ex) {
         throw ex;
      } catch (Exception ex) {
         throw new DataException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
      }
   }

   //    Método auxiliar para convertir una lista de entidades a DTOs
   private List<TaskDto> convertToDtoList(List<Task> tasks) {
      return tasks.stream() // se utiliza stream() y collect(Collectors.toList()) para convertirlo en una lista de DTO
             .map(entity -> mapper.toDto(entity))
             .collect(Collectors.toList());
   }

}
