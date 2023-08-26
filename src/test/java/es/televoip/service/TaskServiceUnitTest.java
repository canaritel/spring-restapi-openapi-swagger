package es.televoip.service;

import es.televoip.model.dto.TaskDto;
import es.televoip.model.mapper.TaskMapper;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
class TaskServiceUnitTest {

   @Autowired
   private TaskService taskService;

   @Autowired
   private TaskMapper taskMapper;

   @Test
   public void testGetTask() {
      // Crear una tarea y guardarla en la base de datos
      TaskDto task = new TaskDto();
      task.setId(1L);
      task.setTitle("Test Task");
      task.setDescription("Integration Test Task");

      taskService.createTask(task);

      // Llamar al método del servicio para obtener la tarea
      TaskDto taskDto = taskService.getTask(task.getId());

      // Verificar que los datos coinciden con lo esperado
      assertThat(taskDto).isNotNull();
      assertThat(taskDto.getTitle()).isEqualTo(task.getTitle());
      assertThat(taskDto.getDescription()).isEqualTo(task.getDescription());
   }

   @Test
   public void testCreateTask() {
      // Crear un DTO de tarea
      TaskDto taskDto = new TaskDto();
      taskDto.setTitle("New Task");
      taskDto.setDescription("Integration Test Create Task");

      // Llamar al método del servicio para crear la tarea
      TaskDto createdTaskDto = taskService.createTask(taskDto);

      // Verificar que se creó correctamente y tiene un ID asignado
      assertThat(createdTaskDto).isNotNull();
      assertThat(createdTaskDto.getId()).isNotNull();

      // Obtener la tarea de la base de datos y verificar que los datos coinciden
      TaskDto createdTask = taskService.getTask(createdTaskDto.getId());
      assertThat(createdTask).isNotNull();
      assertThat(createdTask.getTitle()).isEqualTo(taskDto.getTitle());
      assertThat(createdTask.getDescription()).isEqualTo(taskDto.getDescription());
   }

}
