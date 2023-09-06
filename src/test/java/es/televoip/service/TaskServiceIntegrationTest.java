package es.televoip.service;

import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskStatus;
import es.televoip.model.mapper.TaskMapper;
import es.televoip.repository.TaskRepository;
import es.televoip.service.implement.TaskServiceImpl;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

/*
*  Las pruebas de servicio se centran en la lógica de negocio y cómo interactúa con la capa de persistencia y otras dependencias.
*  Es importante simular todas las interacciones y casos de uso relevantes para garantizar que el servicio funcione como se espera.
*  El objetivo principal es verificar que los métodos en el servicio realicen las operaciones esperadas y se comporten
*  correctamente en respuesta a diferentes entradas.
*
*  Componentes involucrados: Estas pruebas utilizan mocks para simular la capa de acceso a datos y garantizar
*  que el servicio interactúe con el repositorio (base de datos) de la manera correcta.
*
 */
@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
class TaskServiceIntegrationTest {

   @Mock
   private TaskRepository repository;

   @Mock
   private TaskMapper mapper;

   @InjectMocks
   private TaskServiceImpl service;

   @Test
   public void testGetTask() {
      // Configurar comportamiento de repository.findById() y mapper.toDto()
      when(repository.findById(anyLong())).thenReturn(Optional.of(new Task()));
      when(mapper.toDto(any(Task.class))).thenReturn(new TaskDto());

      // Llamar al método del servicio
      TaskDto taskDto = service.findById(1L);

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
   }

   @Test
   public void testGetAllTasks() {
      // Configurar datos de prueba
      List<Task> tasks = Arrays.asList(new Task(), new Task());

      // Configurar comportamiento de repository.findAll() y mapper.toDto()
      when(repository.findAll()).thenReturn(tasks);
      when(mapper.toDto(any(Task.class))).thenReturn(new TaskDto());

      // Llamar al método del servicio
      List<TaskDto> taskDtos = service.findAll();

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findAll();
      verify(mapper, times(tasks.size())).toDto(any(Task.class));
   }

   @Test
   public void testFullGetTask() {
      // Configurar datos de prueba
      Task task = new Task();
      task.setId(1L);

      // Configurar comportamiento de repository.findById() y mapper.toDto()
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
      when(mapper.toDto(any(Task.class))).thenReturn(new TaskDto());

      // Llamar al método del servicio
      TaskDto taskDto = service.findById(1L);

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
      verify(mapper, times(1)).toDto(any(Task.class));

      // Verificar que el DTO recibido no sea nulo y tenga la misma ID que el Task
      assertThat(taskDto).isNotNull();
      if (taskDto.getId() != null) {
         assertThat(taskDto.getId().intValue()).isEqualTo(task.getId().intValue());
      }

      // Verificar que no se realizaron más interacciones con los mocks
      verifyNoMoreInteractions(repository, mapper);
   }

   @Test
   public void testCreateTask() {
      // Configurar datos de prueba
      TaskDto taskDto = new TaskDto();
      Task task = new Task();

      // Configurar comportamiento de repository.save() y mapper.toEntity()
      when(mapper.toEntity(taskDto)).thenReturn(task);
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(new TaskDto()); // Cambiado a toDto

      // Llamar al método del servicio
      TaskDto createdTaskDto = service.save(taskDto);

      // Verificar que los métodos se llamaron correctamente
      verify(mapper, times(1)).toEntity(taskDto);
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task); // Cambiado a toDto
   }

   @Test
   public void testUpdateTask() {
      // Configurar datos de prueba
      TaskDto taskDto = new TaskDto();
      Task task = new Task();

      // Configurar comportamiento de repository.findById(), repository.save() y mapper.toEntity()
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
      when(mapper.toEntity(taskDto)).thenReturn(task);
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(taskDto);

      // Llamar al método del servicio
      TaskDto updatedTaskDto = service.update(1L, taskDto); // actualizamos pasando el 'objeto entero'

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
      verify(mapper, times(1)).toEntity(taskDto); // solo si se pasa el 'objeto entero'
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task);
   }

   @Test
   public void testUpdateTaskStatus() {
      // Configurar datos de prueba
      Task task = new Task();
      TaskDto taskDto = new TaskDto();

      // Configurar comportamiento de repository.findById(), repository.save() y mapper.toDto()
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
      when(mapper.toEntity(taskDto)).thenReturn(task);
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(new TaskDto());

      // Llamar al método del servicio
      TaskDto updatedTaskDto = service.updateTaskStatus(1L, TaskStatus.LATE);

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task);
   }

   @Test
   public void testUpdateTaskIsCompleted() {
      // Configurar datos de prueba
      TaskDto taskDto = new TaskDto();
      Task task = new Task();

      // Configurar comportamiento de repository.findById(), repository.save() y mapper.toDto()
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
      when(mapper.toEntity(taskDto)).thenReturn(task);
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(new TaskDto());

      // Llamar al método del servicio
      TaskDto updatedTaskDto = service.updateTaskIsCompleted(1L, true);

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task);
   }

   @Test
   public void testUpdateTaskDateOfFinished() {
      // Configurar datos de prueba
      LocalDateTime newDateOfFinished = LocalDateTime.of(2023, Month.DECEMBER, 31, 15, 30, 00); // Sin segundos fraccionales      
      TaskDto taskDto = new TaskDto();
      Task task = new Task();

      // Configurar comportamiento de repository.findById(), repository.save() y mapper.toDto()
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
      when(mapper.toEntity(taskDto)).thenReturn(task);
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(taskDto);

      // Llamar al método del servicio
      TaskDto updatedTaskDto = service.updateTaskDateOfFinished(1L, newDateOfFinished);

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task);
   }

   @Test
   public void testDeleteTask() {
      // Configurar comportamiento de repository.findById()
      when(repository.findById(anyLong())).thenReturn(Optional.of(new Task()));

      // Llamar al método del servicio
      service.deleteById(1L);

      // Verificar que repository.delete() se llamó correctamente
      verify(repository, times(1)).deleteById(1L);
   }

}
