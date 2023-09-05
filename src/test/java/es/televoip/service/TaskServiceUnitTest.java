package es.televoip.service;

import es.televoip.exceptions.DataException;
import static es.televoip.factory.TaskDtoDataFactory.create5SampleTaskList;
import static es.televoip.factory.TaskDtoDataFactory.createSampleTask1Default;
import static es.televoip.factory.TaskDtoDataFactory.createSampleTask2Default;
import static es.televoip.factory.TaskDtoDataFactory.createSampleTaskWithId;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.SortField;
import es.televoip.model.enums.TaskStatus;
import es.televoip.service.implement.TaskServiceImpl;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // indicará al marco de pruebas que debe reinicializar el contexto de la aplicación después de cada prueba
@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
class TaskServiceUnitTest {

   @Autowired
   private TaskServiceImpl taskService;

   @Test
   public void testGetTask() {
      // Crear una tarea y guardarla en la base de datos
      TaskDto taskDto = createSampleTask1Default();

      taskService.setSave(taskDto);

      // Llamar al método del servicio para obtener la tarea
      TaskDto newTaskDto = taskService.getById(taskDto.getId());

      // Verificar que los datos coinciden con lo esperado
      assertThat(taskDto).isNotNull();
      assertThat(taskDto.getTitle()).isEqualTo(newTaskDto.getTitle());
      assertThat(taskDto.getDescription()).isEqualTo(newTaskDto.getDescription());
   }

   @Test
   public void testGetAllTask() {
      // Crear algunas tareas y guardarlas en la base de datos
      TaskDto taskDto1 = createSampleTask1Default();
      TaskDto taskDto2 = createSampleTask2Default();

      taskService.setSave(taskDto1);
      taskService.setSave(taskDto2);

      // Llamar al método del servicio para obtener todas las tareas
      List<TaskDto> taskDtos = taskService.getAll();

      // Verificar que se obtuvieron todas las tareas y que los datos coinciden
      assertThat(taskDtos).hasSize(2);
      assertThat(taskDtos.get(0).getTitle()).isEqualTo(taskDto1.getTitle());
      assertThat(taskDtos.get(0).getDescription()).isEqualTo(taskDto1.getDescription());
      assertThat(taskDtos.get(1).getTitle()).isEqualTo(taskDto2.getTitle());
      assertThat(taskDtos.get(1).getDescription()).isEqualTo(taskDto2.getDescription());
   }

   @Test
   public void testGetAllTasksSorted() {
      // Crear algunas tareas y guardarlas en la base de datos con prioridades diferentes
      TaskDto taskDto1 = createSampleTask1Default();
      TaskDto taskDto2 = createSampleTask2Default();

      taskService.setSave(taskDto1);
      taskService.setSave(taskDto2);

      // Llamar al método del servicio para obtener todas las tareas ordenadas por prioridad
      List<TaskDto> taskDtos = taskService.getAllSort(SortField.TASK_TITLE, Sort.Direction.ASC);

      // Verificar que las tareas se obtuvieron ordenadas correctamente y que los datos coinciden
      assertThat(taskDtos).hasSize(2);
      assertThat(taskDtos.get(0).getPriority()).isEqualTo(taskDto1.getPriority());
      assertThat(taskDtos.get(1).getPriority()).isEqualTo(taskDto2.getPriority());
   }

   @Test
   public void testGetAllTasksSortedAndPaginated() {
      // Crear algunas tareas y guardarlas en la base de datos
      taskService.saveAllTasks(create5SampleTaskList());

      // Llamar al método del servicio para obtener todas las tareas ordenadas por prioridad y paginadas
      Pageable pageable = PageRequest.of(0, 3);
      Page<TaskDto> taskPage = taskService.getAllSortdAndPageable(SortField.TASK_TITLE, Sort.Direction.ASC, pageable);

      // Verificar que se obtuvo la página correcta de tareas y que los datos coinciden
      assertThat(taskPage.getContent()).hasSize(pageable.getPageSize());
      assertThat(taskPage.getTotalElements()).isEqualTo(5);
      assertThat(taskPage.getTotalPages()).isEqualTo(2);
      assertThat(taskPage.getNumber()).isEqualTo(pageable.getPageNumber());
   }

   @Test
   public void testGetAllTasksByTitleContaining() {
      // Crear y guardar tareas en la base de datos
      TaskDto taskDto1 = createSampleTaskWithId(
             1L,
             "Title Containing Keyword",
             "Description 1",
             1);
      TaskDto savedTaskDto1 = taskService.setSave(taskDto1);

      TaskDto taskDto2 = createSampleTaskWithId(
             2L,
             "Another Title",
             "Description 2",
             2);
      TaskDto savedTaskDto2 = taskService.setSave(taskDto2);

      // Llamar al método del servicio para obtener tareas por título que contienen una palabra clave
      List<TaskDto> tasksContainingKeyword = taskService.getTasksByTitleContaining("Keyword");

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
      TaskDto taskDto1 = createSampleTask1Default();
      taskDto1.setTaskStatus(TaskStatus.ON_TIME);
      taskService.setSave(taskDto1);

      TaskDto taskDto2 = createSampleTask2Default();
      taskDto2.setTaskStatus(TaskStatus.LATE);
      taskService.setSave(taskDto2);

      // Llamar al método del servicio para obtener tareas por estado
      List<TaskDto> tasksOnTime = taskService.getTasksByTaskStatus(TaskStatus.ON_TIME);
      List<TaskDto> tasksLate = taskService.getTasksByTaskStatus(TaskStatus.LATE);

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
      TaskDto taskDto1 = createSampleTask1Default();
      taskDto1.setIsCompleted(true);
      taskService.setSave(taskDto1);

      TaskDto taskDto2 = createSampleTask2Default();
      taskDto2.setIsCompleted(false);
      taskService.setSave(taskDto2);

      // Llamar al método del servicio para obtener tareas por estado de completitud
      List<TaskDto> completedTasks = taskService.getTasksByCompletion(true);
      List<TaskDto> incompleteTasks = taskService.getTasksByCompletion(false);

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
      TaskDto taskDto1 = createSampleTask1Default();

      // Llamar al método del servicio para crear la tarea
      TaskDto createdTaskDto = taskService.setSave(taskDto1);

      // Verificar que se creó correctamente y tiene un ID asignado
      assertThat(createdTaskDto).isNotNull();
      assertThat(createdTaskDto.getId()).isNotNull();

      // Obtener la tarea de la base de datos y verificar que los datos coinciden
      TaskDto createdTask = taskService.getById(createdTaskDto.getId());
      assertThat(createdTask).isNotNull();
      assertThat(createdTask.getTitle()).isEqualTo(taskDto1.getTitle());
      assertThat(createdTask.getDescription()).isEqualTo(taskDto1.getDescription());
   }

   /*
   @Test
   public void testCreateAllTasks() {
      // Crear una lista de DTOs de tareas
      List<TaskDto> taskDtos = create3SampleTaskList();

      // Llamar al método del servicio para crear todas las tareas
      List<TaskDto> createdTasks = taskService.createAllTasks(taskDtos);

      // Verificar que se crearon correctamente y tienen IDs asignados
      assertThat(createdTasks).isNotNull();
      assertThat(createdTasks).hasSize(3);
      assertThat(createdTasks.get(0).getId()).isNotNull();
      assertThat(createdTasks.get(1).getId()).isNotNull();

      // Obtener las tareas de la base de datos y verificar que los datos coinciden
      TaskDto createdTask1 = taskService.getById(createdTasks.get(0).getId());
      assertThat(createdTask1).isNotNull();
      assertThat(createdTask1.getTitle()).isEqualTo(createdTasks.get(0).getTitle());
      assertThat(createdTask1.getDescription()).isEqualTo(createdTasks.get(0).getDescription());

      TaskDto createdTask2 = taskService.getTask(createdTasks.get(1).getId());
      assertThat(createdTask2).isNotNull();
      assertThat(createdTask2.getTitle()).isEqualTo(createdTasks.get(1).getTitle());
      assertThat(createdTask2.getDescription()).isEqualTo(createdTasks.get(1).getDescription());

      TaskDto createdTask3 = taskService.getTask(createdTasks.get(2).getId());
      assertThat(createdTask3).isNotNull();
      assertThat(createdTask3.getTitle()).isEqualTo(createdTasks.get(2).getTitle());
      assertThat(createdTask3.getDescription()).isEqualTo(createdTasks.get(2).getDescription());
   }
    */
   @Test
   public void testUpdateTask() {
      // Crear un DTO de tarea existente
      TaskDto taskDto = createSampleTaskWithId(
             1L,
             "Existing Task",
             "Existing Task Description",
             1);

      // Grabamos el objeto DTO
      TaskDto savedTaskDto = taskService.setSave(taskDto);

      // Crear un DTO de tarea actualizada
      TaskDto updatedTaskDto = createSampleTaskWithId(
             savedTaskDto.getId(),
             "Updated Task",
             "Updated Task Description",
             2);

      // Actualizar la tarea utilizando el servicio
      TaskDto updatedTask = taskService.setUpdate(updatedTaskDto.getId(), updatedTaskDto);

      // Verificar que la tarea se actualizó correctamente
      assertThat(updatedTask).isNotNull();
      assertThat(updatedTask.getId()).isEqualTo(updatedTaskDto.getId());
      assertThat(updatedTask.getTitle()).isEqualTo(updatedTaskDto.getTitle());
      assertThat(updatedTask.getDescription()).isEqualTo(updatedTaskDto.getDescription());
   }

   @Test
   public void testUpdateTaskDateOfFinished() {
      // Crear y guardar una tarea en la base de datos
      TaskDto taskDto = createSampleTask1Default();
      TaskDto savedTaskDto = taskService.setSave(taskDto);

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
      TaskDto taskDto = createSampleTask1Default();
      TaskDto savedTaskDto = taskService.setSave(taskDto);

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
      TaskDto taskDto = createSampleTask1Default();
      TaskDto savedTaskDto = taskService.setSave(taskDto);

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
      TaskDto taskDto = createSampleTask1Default();
      TaskDto savedTaskDto = taskService.setSave(taskDto);

      // Llamar al método del servicio para actualizar la tarea a completada
      TaskDto updatedTask = taskService.markTaskAsCompleted(savedTaskDto.getId());

      // Verificar que la tarea se actualizó a completada correctamente
      assertThat(updatedTask).isNotNull();
      assertThat(updatedTask.getIsCompleted()).isEqualTo(true);
   }

   @Test
   public void testDeleteTask() {
      // Crear una tarea y guardarla en la base de datos
      TaskDto taskDto = createSampleTask1Default();

      // Grabamos el objeto DTO
      TaskDto savedTaskDto = taskService.setSave(taskDto);

      // Obtener el ID de la tarea recién creada
      Long taskId = savedTaskDto.getId();
      System.out.println("ID: " + taskId);

      // Borrar la tarea utilizando el servicio
      taskService.setDeleteById(taskId);

      // Verificar que una excepción de tipo TaskException es lanzada al intentar obtener la tarea eliminada
      DataException assertThrows = assertThrows(DataException.class, () -> {
         taskService.getById(taskId);
      });
      assertEquals(assertThrows.getErrorMessage(), "Tarea no encontrada con ID: " + taskId);
   }

   @Test
   public void testCreateAllTasks_EmptyList() throws Exception {
      // Intentar crear una lista vacía de tareas
      List<TaskDto> emptyList = new ArrayList<>();

      // Utilizar assertThrows para verificar que se lanza una excepción
      DataException assertThrows = assertThrows(DataException.class, () -> {
         taskService.saveAllTasks(emptyList);
      });

      // Verificar el mensaje de error en la excepción
      assertThat(assertThrows.getErrorMessage()).isEqualTo("La lista de tareas a crear es 'null'.");
   }

}
