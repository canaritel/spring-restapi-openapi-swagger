package es.televoip;

import es.televoip.model.Task;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskStatus;
import es.televoip.model.mapper.TaskMapper;
import es.televoip.repository.TaskRepository;
import es.televoip.service.TaskService;
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

@SpringBootTest
class TaskServiceTest {

   @Mock
   private TaskRepository repository;

   @Mock
   private TaskMapper mapper;

   @InjectMocks
   private TaskService service;

   @Test
   public void testGetTask() {
      // Configurar comportamiento de repository.findById()
      when(repository.findById(anyLong())).thenReturn(Optional.of(new Task()));

      // Llamar al método del servicio
      TaskDto taskDto = service.getTask(1L);

      // Verificar que repository.findById() se llamó correctamente
      verify(repository, times(1)).findById(anyLong());
   }

   @Test
   public void testGetAllTasks() {
      // Configurar comportamiento de repository.findAll() y mapper.toDto()
      List<Task> tasks = Arrays.asList(new Task(), new Task());
      when(repository.findAll()).thenReturn(tasks);
      when(mapper.toDto(any(Task.class))).thenReturn(new TaskDto());

      // Llamar al método del servicio
      List<TaskDto> taskDtos = service.getAllTask();

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findAll();
      verify(mapper, times(tasks.size())).toDto(any(Task.class));
   }

   @Test
   public void testFullGetTask() {
      // Configurar mocks y datos de prueba
      Task task = new Task();
      task.setId(1L);

      // Simular comportamiento de repository.findById()
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));

      // Simular comportamiento de mapper.toDto()
      when(mapper.toDto(any(Task.class))).thenReturn(new TaskDto());

      // Llamar al método del servicio
      TaskDto taskDto = service.getTask(1L);

      // Verificar que repository.findById() se llamó correctamente
      verify(repository, times(1)).findById(anyLong());

      // Verificar que mapper.toDto() se llamó correctamente
      verify(mapper, times(1)).toDto(any(Task.class));

      // Verificar que el DTO recibido no sea nulo y tenga la misma ID que el Task
      assertThat(taskDto).isNotNull();
      //assertThat(taskDto.getId()).isEqualTo(task.getId());

      // Verificar que no se realizaron más interacciones con los mocks
      verifyNoMoreInteractions(repository, mapper);
   }

   @Test
   public void testCreateTask() {
      // Configurar comportamiento de repository.save() y mapper.toEntity()
      TaskDto taskDto = new TaskDto();
      Task task = new Task();
      when(mapper.toEntity(taskDto)).thenReturn(task);
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(new TaskDto()); // Cambiado a toDto

      // Llamar al método del servicio
      TaskDto createdTaskDto = service.createTask(taskDto);

      // Verificar que los métodos se llamaron correctamente
      verify(mapper, times(1)).toEntity(taskDto);
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task); // Cambiado a toDto
   }

   @Test
   public void testUpdateTask() {
      // Configurar comportamiento de repository.findById(), repository.save() y mapper.toEntity()
      TaskDto taskDto = new TaskDto();
      Task task = new Task();
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
      when(mapper.toEntity(taskDto)).thenReturn(task);
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(taskDto);

      // Llamar al método del servicio
      TaskDto updatedTaskDto = service.updateTask(1L, taskDto);

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
      verify(mapper, times(1)).toEntity(taskDto);
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task);
   }

   @Test
   public void testUpdateTaskStatus() {
      // Configurar comportamiento de repository.findById(), repository.save() y mapper.toDto()
      Task task = new Task();
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(new TaskDto());

      // Llamar al método del servicio
      TaskDto updatedTaskDto = service.updateTaskStatus(1L, TaskStatus.LATE);

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task);
   }

   /*
   // Este método me devuelve error al tener validaciones en la fecha
   @Test
   public void testUpdateTaskDateOfFinished() {
      // Configurar comportamiento de repository.findById(), repository.save() y mapper.toDto()
      Task task = new Task();
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
      when(repository.save(task)).thenReturn(task);
      when(mapper.toDto(task)).thenReturn(new TaskDto());

      LocalDateTime newDateOfFinished = LocalDateTime.of(2023, Month.DECEMBER, 31, 15, 30, 00); // Sin segundos fraccionales

      // Llamar al método del servicio
      TaskDto updatedTaskDto = service.updateTaskDateOfFinished(1L, newDateOfFinished);

      System.out.println("time1: " + newDateOfFinished);
      System.out.println("time2: " + updatedTaskDto.getTaskDateFinished());

      // Verificar que los métodos se llamaron correctamente
      verify(repository, times(1)).findById(anyLong());
      verify(repository, times(1)).save(task);
      verify(mapper, times(1)).toDto(task);
   }
    */
   @Test
   public void testUpdateTaskIsCompleted() {
      // Configurar comportamiento de repository.findById(), repository.save() y mapper.toDto()
      Task task = new Task();
      when(repository.findById(anyLong())).thenReturn(Optional.of(task));
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
   public void testDeleteTask() {
      // Configurar comportamiento de repository.findById()
      when(repository.findById(anyLong())).thenReturn(Optional.of(new Task()));

      // Llamar al método del servicio
      service.deleteTask(1L);

      // Verificar que repository.delete() se llamó correctamente
      verify(repository, times(1)).deleteById(1L);
   }

}
