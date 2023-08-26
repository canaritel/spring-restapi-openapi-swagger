package es.televoip.service;

import es.televoip.exceptions.TaskException;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskSortField;
import es.televoip.model.enums.TaskStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // indicará al marco de pruebas que debe reinicializar el contexto de la aplicación después de cada prueba
@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
class TaskServiceUnitTest {

   @Autowired
   private TaskService taskService;

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
   public void testGetAllTask() {
      // Crear algunas tareas y guardarlas en la base de datos
      TaskDto task1 = new TaskDto();
      task1.setTitle("Task 1");
      task1.setDescription("Description 1");

      TaskDto task2 = new TaskDto();
      task2.setTitle("Task 2");
      task2.setDescription("Description 2");

      taskService.createTask(task1);
      taskService.createTask(task2);

      // Llamar al método del servicio para obtener todas las tareas
      List<TaskDto> taskDtos = taskService.getAllTask();

      // Verificar que se obtuvieron todas las tareas y que los datos coinciden
      assertThat(taskDtos).hasSize(2);
      assertThat(taskDtos.get(0).getTitle()).isEqualTo(task1.getTitle());
      assertThat(taskDtos.get(0).getDescription()).isEqualTo(task1.getDescription());
      assertThat(taskDtos.get(1).getTitle()).isEqualTo(task2.getTitle());
      assertThat(taskDtos.get(1).getDescription()).isEqualTo(task2.getDescription());
   }

   @Test
   public void testGetAllTasksSorted() {
      // Crear algunas tareas y guardarlas en la base de datos con prioridades diferentes
      TaskDto task1 = new TaskDto();
      task1.setTitle("Task 1");
      task1.setDescription("Description 1");
      task1.setPriority(2);

      TaskDto task2 = new TaskDto();
      task2.setTitle("Task 2");
      task2.setDescription("Description 2");
      task2.setPriority(1);

      taskService.createTask(task1);
      taskService.createTask(task2);

      // Llamar al método del servicio para obtener todas las tareas ordenadas por prioridad
      List<TaskDto> taskDtos = taskService.getAllTasksSorted(TaskSortField.TITLE, Sort.Direction.ASC);

      // Verificar que las tareas se obtuvieron ordenadas correctamente y que los datos coinciden
      assertThat(taskDtos).hasSize(2);
      assertThat(taskDtos.get(0).getPriority()).isEqualTo(task1.getPriority());
      assertThat(taskDtos.get(1).getPriority()).isEqualTo(task2.getPriority());
   }

   @Test
   public void testGetAllTasksSortedAndPaginated() {
      // Crear algunas tareas y guardarlas en la base de datos
      for (int i = 1; i <= 5; i++) {
         TaskDto task = new TaskDto();
         task.setTitle("Task " + i);
         task.setDescription("Description " + i);
         taskService.createTask(task);
      }

      // Llamar al método del servicio para obtener todas las tareas ordenadas por prioridad y paginadas
      int page = 0;
      int size = 3;
      Page<TaskDto> taskPage = taskService.getAllTasksSortedAndPaginated(TaskSortField.TITLE, Sort.Direction.ASC, page, size);

      // Verificar que se obtuvo la página correcta de tareas y que los datos coinciden
      assertThat(taskPage.getContent()).hasSize(size);
      assertThat(taskPage.getTotalElements()).isEqualTo(5);
      assertThat(taskPage.getTotalPages()).isEqualTo(2);
      assertThat(taskPage.getNumber()).isEqualTo(page);
   }

   @Test
   public void testGetAllTasksByTitleContaining() {
      // Crear y guardar tareas en la base de datos
      TaskDto taskDto1 = new TaskDto();
      taskDto1.setTitle("Title Containing Keyword");
      taskDto1.setDescription("Description 1");
      TaskDto savedTaskDto1 = taskService.createTask(taskDto1);

      TaskDto taskDto2 = new TaskDto();
      taskDto2.setTitle("Another Title");
      taskDto2.setDescription("Description 2");
      TaskDto savedTaskDto2 = taskService.createTask(taskDto2);

      // Llamar al método del servicio para obtener tareas por título que contienen una palabra clave
      List<TaskDto> tasksContainingKeyword = taskService.getAllTasksByTitleContaining("Keyword");

      // Verificar que se obtuvieron las tareas con título que contiene la palabra clave
      assertThat(tasksContainingKeyword).isNotNull();
      assertThat(tasksContainingKeyword).hasSize(1);

      // Verificar que los datos coinciden con la tarea guardada
      assertThat(tasksContainingKeyword.get(0).getId()).isEqualTo(savedTaskDto1.getId());
      assertThat(tasksContainingKeyword.get(0).getTitle()).isEqualTo(savedTaskDto1.getTitle());
      assertThat(tasksContainingKeyword.get(0).getDescription()).isEqualTo(savedTaskDto1.getDescription());
   }

   @Test
   public void testGetAllByTaskStatus() {
      // Crear y guardar tareas en la base de datos con diferentes estados
      TaskDto task1 = new TaskDto();
      task1.setTitle("Task 1");
      task1.setDescription("Description 1");
      task1.setTaskStatus(TaskStatus.ON_TIME);
      taskService.createTask(task1);

      TaskDto task2 = new TaskDto();
      task2.setTitle("Task 2");
      task2.setDescription("Description 2");
      task2.setTaskStatus(TaskStatus.LATE);
      taskService.createTask(task2);

      // Llamar al método del servicio para obtener tareas por estado
      List<TaskDto> tasksOnTime = taskService.getAllByTaskStatus(TaskStatus.ON_TIME);
      List<TaskDto> tasksLate = taskService.getAllByTaskStatus(TaskStatus.LATE);

      // Verificar que se recuperaron las tareas con los estados correctos
      assertThat(tasksOnTime).isNotEmpty();
      assertThat(tasksLate).isNotEmpty();
      assertThat(tasksOnTime).hasSize(1);
      assertThat(tasksLate).hasSize(1);
      assertThat(tasksOnTime.get(0).getTaskStatus()).isEqualTo(TaskStatus.ON_TIME);
      assertThat(tasksLate.get(0).getTaskStatus()).isEqualTo(TaskStatus.LATE);
   }

   @Test
   public void testGetAllTasksByCompletionStatus() {
      // Crear y guardar tareas en la base de datos con diferentes estados de completitud
      TaskDto task1 = new TaskDto();
      task1.setTitle("Task 1");
      task1.setDescription("Description 1");
      task1.setIsCompleted(true);
      taskService.createTask(task1);

      TaskDto task2 = new TaskDto();
      task2.setTitle("Task 2");
      task2.setDescription("Description 2");
      task2.setIsCompleted(false);
      taskService.createTask(task2);

      // Llamar al método del servicio para obtener tareas por estado de completitud
      List<TaskDto> completedTasks = taskService.getAllTasksByCompletionStatus(true);
      List<TaskDto> incompleteTasks = taskService.getAllTasksByCompletionStatus(false);

      // Verificar que se recuperaron las tareas con los estados de completitud correctos
      assertThat(completedTasks).isNotEmpty();
      assertThat(incompleteTasks).isNotEmpty();
      assertThat(completedTasks).hasSize(1);
      assertThat(incompleteTasks).hasSize(1);
      assertThat(completedTasks.get(0).getIsCompleted()).isEqualTo(true);
      assertThat(incompleteTasks.get(0).getIsCompleted()).isEqualTo(false);
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

   @Test
   public void testCreateAllTasks() {
      // Crear una lista de DTOs de tareas
      List<TaskDto> taskDtos = new ArrayList<>();
      TaskDto task1 = new TaskDto();
      task1.setTitle("Task 1");
      task1.setDescription("Description 1");
      taskDtos.add(task1);

      TaskDto task2 = new TaskDto();
      task2.setTitle("Task 2");
      task2.setDescription("Description 2");
      taskDtos.add(task2);

      // Llamar al método del servicio para crear todas las tareas
      List<TaskDto> createdTasks = taskService.createAllTasks(taskDtos);

      // Verificar que se crearon correctamente y tienen IDs asignados
      assertThat(createdTasks).isNotNull();
      assertThat(createdTasks).hasSize(2);
      assertThat(createdTasks.get(0).getId()).isNotNull();
      assertThat(createdTasks.get(1).getId()).isNotNull();

      // Obtener las tareas de la base de datos y verificar que los datos coinciden
      TaskDto createdTask1 = taskService.getTask(createdTasks.get(0).getId());
      assertThat(createdTask1).isNotNull();
      assertThat(createdTask1.getTitle()).isEqualTo(task1.getTitle());
      assertThat(createdTask1.getDescription()).isEqualTo(task1.getDescription());

      TaskDto createdTask2 = taskService.getTask(createdTasks.get(1).getId());
      assertThat(createdTask2).isNotNull();
      assertThat(createdTask2.getTitle()).isEqualTo(task2.getTitle());
      assertThat(createdTask2.getDescription()).isEqualTo(task2.getDescription());
   }

   @Test
   public void testUpdateTask() {
      // Crear un DTO de tarea existente
      TaskDto taskDto = new TaskDto();
      taskDto.setTitle("Existing Task");
      taskDto.setDescription("Existing Task Description");

      // Grabamos el objeto DTO
      TaskDto savedTaskDto = taskService.createTask(taskDto);

      // Crear un DTO de tarea actualizada
      TaskDto updatedTaskDto = new TaskDto();
      updatedTaskDto.setId(savedTaskDto.getId());
      updatedTaskDto.setTitle("Updated Task");
      updatedTaskDto.setDescription("Updated Task Description");

      // Actualizar la tarea utilizando el servicio
      TaskDto updatedTask = taskService.updateTask(updatedTaskDto.getId(), updatedTaskDto);

      // Verificar que la tarea se actualizó correctamente
      assertThat(updatedTask).isNotNull();
      assertThat(updatedTask.getId()).isEqualTo(updatedTaskDto.getId());
      assertThat(updatedTask.getTitle()).isEqualTo(updatedTaskDto.getTitle());
      assertThat(updatedTask.getDescription()).isEqualTo(updatedTaskDto.getDescription());
   }

   @Test
   public void testUpdateTaskDateOfFinished() {
      // Crear y guardar una tarea en la base de datos
      TaskDto taskDto = new TaskDto();
      taskDto.setTitle("Task to Update Date");
      taskDto.setDescription("Task to Update Date Description");
      TaskDto savedTaskDto = taskService.createTask(taskDto);

      // Crear una nueva fecha de finalización
      LocalDateTime newDateOfFinished = LocalDateTime.now().plusDays(1);

      // Llamar al método del servicio para actualizar la fecha de finalización
      TaskDto updatedTask = taskService.updateTaskDateOfFinished(savedTaskDto.getId(), newDateOfFinished);

      // Verificar que la fecha de finalización se actualizó correctamente
      assertThat(updatedTask).isNotNull();
      assertThat(updatedTask.getTaskDateFinished()).isEqualTo(newDateOfFinished);
   }

   @Test
   public void testUpdateTaskStatus() {
      // Crear y guardar una tarea en la base de datos
      TaskDto taskDto = new TaskDto();
      taskDto.setTitle("Task to Update Status");
      taskDto.setDescription("Task to Update Status Description");
      TaskDto savedTaskDto = taskService.createTask(taskDto);

      // Crear un nuevo estado de tarea
      TaskStatus newTaskStatus = TaskStatus.LATE;

      // Llamar al método del servicio para actualizar el estado de la tarea
      TaskDto updatedTask = taskService.updateTaskStatus(savedTaskDto.getId(), newTaskStatus);

      // Verificar que el estado de la tarea se actualizó correctamente
      assertThat(updatedTask).isNotNull();
      assertThat(updatedTask.getTaskStatus()).isEqualTo(newTaskStatus);
   }

   @Test
   public void testUpdateTaskIsCompleted() {
      // Crear y guardar una tarea en la base de datos
      TaskDto taskDto = new TaskDto();
      taskDto.setTitle("Task to Update Is Completed");
      taskDto.setDescription("Task to Update Is Completed Description");
      TaskDto savedTaskDto = taskService.createTask(taskDto);

      // Crear un nuevo estado de completitud
      Boolean newIsCompleted = true;

      // Llamar al método del servicio para actualizar el estado de completitud de la tarea
      TaskDto updatedTask = taskService.updateTaskIsCompleted(savedTaskDto.getId(), newIsCompleted);

      // Verificar que el estado de completitud de la tarea se actualizó correctamente
      assertThat(updatedTask).isNotNull();
      assertThat(updatedTask.getIsCompleted()).isEqualTo(newIsCompleted);
   }

   @Test
   public void testUpdateTaskToCompleted() {
      // Crear y guardar una tarea en la base de datos
      TaskDto taskDto = new TaskDto();
      taskDto.setTitle("Task to Update to Completed");
      taskDto.setDescription("Task to Update to Completed Description");
      TaskDto savedTaskDto = taskService.createTask(taskDto);

      // Llamar al método del servicio para actualizar la tarea a completada
      TaskDto updatedTask = taskService.updateTaskToCompleted(savedTaskDto.getId());

      // Verificar que la tarea se actualizó a completada correctamente
      assertThat(updatedTask).isNotNull();
      assertThat(updatedTask.getIsCompleted()).isEqualTo(true);
   }

   @Test
   public void testDeleteTask() {
      // Crear una tarea y guardarla en la base de datos
      TaskDto taskDto = new TaskDto();
      taskDto.setTitle("Task to Delete");
      taskDto.setDescription("Task to Delete Description");

      // Grabamos el objeto DTO
      TaskDto savedTaskDto = taskService.createTask(taskDto);

      // Obtener el ID de la tarea recién creada
      Long taskId = savedTaskDto.getId();
      System.out.println("ID: " + taskId);

      // Borrar la tarea utilizando el servicio
      taskService.deleteTask(taskId);

      // Verificar que una excepción de tipo TaskException es lanzada al intentar obtener la tarea eliminada
      TaskException assertThrows = assertThrows(TaskException.class, () -> {
         taskService.getTask(taskId);
      });
      assertEquals(assertThrows.getErrorMessage(), "Tarea no encontrada con ID: " + taskId);
   }

   @Test
   public void testCreateAllTasks_EmptyList() throws Exception {
      // Intentar crear una lista vacía de tareas
      List<TaskDto> emptyList = new ArrayList<>();

      // Utilizar assertThrows para verificar que se lanza una excepción
      TaskException assertThrows = assertThrows(TaskException.class, () -> {
         taskService.createAllTasks(emptyList);
      });

      // Verificar el mensaje de error en la excepción
      assertThat(assertThrows.getErrorMessage()).isEqualTo("La lista de tareas a crear es 'null'.");
   }

}
