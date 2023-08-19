package es.televoip.controller;

import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskSortField;
import es.televoip.model.enums.TaskStatus;
import es.televoip.service.TaskService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class taskController {

   // Accesos para OPENAPI Swagger3
   // http://localhost:8080/swagger-ui/index.html
   // http://localhost:8080/v3/api-docs
   //
   private final TaskService service;

   public taskController(TaskService service) {
      this.service = service;
   }

   final String VALUE_OK = "{"
          + "\"id\": \"long\","
          + "\"title\": \"string\","
          + "\"description\": \"string\","
          + "\"taskStatus\": \"[ON_TIME , LATE]\","
          + "\"isCompleted\": \"boolean\","
          + "\"priority\": \"int\","
          + "\"taskDateCreation\": \"localdatetime\","
          + "\"taskDateFinished\": \"localdatetime\""
          + "}";

   final String VALUE_ERROR_404 = "{"
          + "\"status\": \"error\","
          + "\"message\": \"La tarea no fue encontrada\""
          + "}";

   final String VALUE_ERROR_400 = "{"
          + "\"status\": \"error\","
          + "\"message\": \"La solicitud enviada no es válida.\""
          + "}";

   final String VALUE_ERROR_422 = "{"
          + "\"status\": \"error\","
          + "\"message\": \"La tarea no cumple las validaciones.\""
          + "}";

   // ********************************************************************************************************
   /**
    * getTask: Obtiene una tarea por su ID.
    *
    * @param id
    * @return Respuesta con el objeto tarea.
    * @apiNote Devuelve una tarea correspondiente al ID proporcionado.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Tarea obtenida exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Obtención de la tarea",
                              description = "Se obtiene un objeto de tipo tarea.",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = "Tarea no encontrada.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "La tarea con el ID especificado no se encuentra.",
                              value = VALUE_ERROR_404
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )}))
   })
   @GetMapping("/{id}")
   public ResponseEntity<TaskDto> getTask(@PathVariable Long id) {
      TaskDto taskDto = service.getTask(id);
      if (taskDto != null) {
         return new ResponseEntity<>(taskDto, HttpStatus.OK);
      } else {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
   }

   // ********************************************************************************************************
   /**
    * getAllTasks: Obtiene todas las tareas.
    *
    * @return Respuesta con la lista de objetos tarea.
    * @apiNote Devuelve una lista de todas las tareas disponibles.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Tarea obtenida exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Obtención de todas las tareas",
                              description = "Se obtiene un objeto de tipo listado tareas.",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )}))
   })
   @GetMapping("/all")
   public ResponseEntity<List<TaskDto>> getAllTasks() {
      List<TaskDto> tasks = service.getAllTask();
      return new ResponseEntity<>(tasks, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * getAllTasksSorted: Obtiene todas las tareas ordenadas.
    *
    * @param sortBy Campo por el cual se ordenarán las tareas.
    * @param sortOrder Dirección de la ordenación (ASCENDENTE o DESCENDENTE).
    * @return Respuesta con la lista de objetos tarea ordenada.
    * @apiNote Devuelve una lista de todas las tareas ordenadas según el campo y dirección proporcionados.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Tarea obtenida exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Obtención de todas las tareas ordenadas",
                              description = "Se obtiene un objeto de tipo listado tareas ordenadas.",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )}))
   })
   @GetMapping("/sorted")
   public ResponseEntity<List<TaskDto>> getAllTasksSorted(@RequestParam TaskSortField sortBy,
          @RequestParam Sort.Direction sortOrder) {
      List<TaskDto> tasks = service.getAllTasksSorted(sortBy, sortOrder);
      return new ResponseEntity<>(tasks, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * getAllTasksSortedAndPaginated: Obtiene todas las tareas ordenadas y paginadas.
    *
    * @param sortBy Campo por el cual se ordenarán las tareas.
    * @param sortOrder Dirección de la ordenación (ASCENDENTE o DESCENDENTE).
    * @param page Número de página para la paginación.
    * @param size Tamaño de página para la paginación.
    * @return Respuesta con la lista de objetos tarea ordenada y paginada.
    * @apiNote Devuelve una lista de todas las tareas ordenadas y paginadas según los parámetros proporcionados.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Tarea obtenida exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Obtención de todas las tareas ordenadas y paginadas",
                              description = "Se obtiene un objeto de tipo listado tareas ordenadas y paginadas.",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )}))
   })
   @GetMapping("/sortedAndPaginated")
   public ResponseEntity<Page<TaskDto>> getAllTasksSortedAndPaginated(@RequestParam TaskSortField sortBy,
          @RequestParam Sort.Direction sortOrder, @RequestParam int page, @RequestParam int size) {
      Page<TaskDto> taskPage = service.getAllTasksSortedAndPaginated(sortBy, sortOrder, page, size);
      return new ResponseEntity<>(taskPage, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * getAllByTaskStatus: Obtiene todas las tareas según su estado.
    *
    * @param status Estado de las tareas a obtener.
    * @return Respuesta con la lista de objetos tarea correspondientes al estado.
    * @apiNote Devuelve una lista de todas las tareas que coinciden con el estado proporcionado.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Tareas obtenidas exitosamente por estado.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Obtención de tareas por estado",
                              description = "Se obtiene un objeto de tipo listado de tareas según el estado.",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = "No se encontraron tareas con el estado proporcionado.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tareas no encontradas",
                              description = "No se encontraron tareas con el estado proporcionado.",
                              value = VALUE_ERROR_404
                       )}))
   })
   @GetMapping("/status/{status}")
   public ResponseEntity<List<TaskDto>> getAllByTaskStatus(@PathVariable("status") TaskStatus status) {
      List<TaskDto> tasks = service.getAllByTaskStatus(status);
      return new ResponseEntity<>(tasks, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * createTask: Crea una nueva tarea.
    *
    * @param taskDto Datos de la tarea a crear.
    * @return Respuesta con la tarea creada.
    * @apiNote Crea una nueva tarea con los datos proporcionados en el cuerpo de la solicitud.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "201",
             description = "Tarea creada exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Creación de tarea exitosa",
                              description = """
                                 Los campos 'title' y 'description' son de tipo String. 
                                 El campo 'priority' va del 1 (mínima prioridad) a 9 (máxima prioridad).""",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "422",
             description = "No se pudo procesar la solicitud.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "Revise si los campos cumplen con las validaciones.",
                              value = VALUE_ERROR_422
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )}))
   })
   @PostMapping
   public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
      // Valid debe tener anotaciones de validación adecuadas, como @NotBlank, @NotNull, @Size, etc., en su capa DTO
      TaskDto createdTask = service.createTask(taskDto);
      return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
   }

   // ********************************************************************************************************
   /**
    * updateTask: Actualiza una tarea existente.
    *
    * @param id El ID de la tarea a actualizar.
    * @param taskDto Datos actualizados de la tarea.
    * @return Respuesta con la tarea actualizada.
    * @apiNote Actualiza la tarea correspondiente al ID proporcionado con los datos proporcionados en el cuerpo de la solicitud.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Tarea actualizada exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Actualización de la tarea",
                              description = """
                                 Los campos 'title' y 'description' son de tipo String. 
                                 El campo 'priority' va del 1 (mínima prioridad) a 9 (máxima prioridad).""",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = "Tarea no encontrada.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = """
                                 La tarea con el ID especificado no se encuentra.""",
                              value = VALUE_ERROR_404
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )})),
      @ApiResponse(
             responseCode = "422",
             description = "No se pudo procesar la solicitud.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "Revise si los campos cumplen con las validaciones.",
                              value = VALUE_ERROR_422
                       )}))
   })
   @PutMapping("/{id}")
   public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @Valid @RequestBody TaskDto taskDto) {
      // Valid debe tener anotaciones de validación adecuadas, como @NotBlank, @NotNull, @Size, etc., en su capa DTO
      TaskDto updatedTask = service.updateTask(id, taskDto);
      return new ResponseEntity<>(updatedTask, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * updateTaskDateOfFinished: Actualiza la fecha de finalización de una tarea.
    *
    * @param id El ID de la tarea a actualizar.
    * @param newDateOfFinished Nueva fecha de finalización de la tarea.
    * @return Respuesta con la tarea actualizada.
    * @apiNote Actualiza la fecha de finalización de la tarea correspondiente al ID proporcionado con la nueva fecha especificada.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Fecha de finalización actualizada exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Actualización de fecha de finalización",
                              description = """
                                 El campo es un objeto de tipo LocalDateTime que indica la fecha de finalización de la tarea. 
                                 Permite el envío de 'null'.""",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = "Tarea no encontrada.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = """
                                 La tarea con el ID especificado no se encuentra.""",
                              value = VALUE_ERROR_404
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )})),
      @ApiResponse(
             responseCode = "422",
             description = "No se pudo procesar la solicitud.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "Revise si los campos cumplen con las validaciones.",
                              value = VALUE_ERROR_422
                       )}))
   })
   @PatchMapping("/{id}/dateOfFinished")
   public ResponseEntity<TaskDto> updateTaskDateOfFinished(@PathVariable Long id, @RequestParam LocalDateTime newDateOfFinished) {
      TaskDto updatedTask = service.updateTaskDateOfFinished(id, newDateOfFinished);
      return new ResponseEntity<>(updatedTask, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * updateTaskStatus: Actualiza el estado de una tarea.
    *
    * @param id El ID de la tarea a actualizar.
    * @param taskStatus Nuevo estado de la tarea.
    * @return Respuesta con la tarea actualizada.
    * @apiNote Actualiza el estado de la tarea correspondiente al ID proporcionado con el nuevo estado especificado.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Estado de la tarea actualizada exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Actualización del estado de la tarea",
                              description = """
                                 El campo 'taskStatus' es un objeto de tipo enum que indica el estado actual de la tarea.
                                 Por defecto enviar 'ON_TIME'.""",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = "Tarea no encontrada.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = """
                                 La tarea con el ID especificado no se encuentra.""",
                              value = VALUE_ERROR_404
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )})),
      @ApiResponse(
             responseCode = "422",
             description = "No se pudo procesar la solicitud.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "Revise si los campos cumplen con las validaciones.",
                              value = VALUE_ERROR_422
                       )}))
   })
   @PatchMapping("/{id}/status")
   public ResponseEntity<TaskDto> updateTaskStatus(@PathVariable Long id, @RequestParam TaskStatus taskStatus) {
      TaskDto updatedStatus = service.updateTaskStatus(id, taskStatus);
      return new ResponseEntity<>(updatedStatus, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * updateTaskIsCompleted: Actualiza el estado de completitud de una tarea.
    *
    * @param id El ID de la tarea a actualizar.
    * @param isCompleted Nuevo estado de completitud de la tarea.
    * @return Respuesta con la tarea actualizada.
    * @apiNote Actualiza el estado de finalización de la tarea correspondiente al ID proporcionado con el nuevo estado de finalización
    * especificado.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Estado para indicar si la tarea está completada.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea completada",
                              description = """
                                 El campo es un objeto de tipo Boolean que indica si se ha completado la tarea.
                                 Para confirmar está completada envíe 'TRUE'.""",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = "Tarea no encontrada.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = """
                                 La tarea con el ID especificado no se encuentra.""",
                              value = VALUE_ERROR_404
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )})),
      @ApiResponse(
             responseCode = "422",
             description = "No se pudo procesar la solicitud.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "Revise si los campos cumplen con las validaciones.",
                              value = VALUE_ERROR_422
                       )}))
   })
   @PatchMapping("/{id}/isCompleted")
   public ResponseEntity<TaskDto> updateTaskIsCompleted(@PathVariable Long id, @RequestParam Boolean isCompleted) {
      TaskDto updatedCompleted = service.updateTaskIsCompleted(id, isCompleted);
      return new ResponseEntity<>(updatedCompleted, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * updateTaskToCompleted: Actualiza el estado de una tarea a completada.
    *
    * @param id El ID de la tarea a actualizar.
    * @return Respuesta con la tarea actualizada a completada.
    * @apiNote Actualiza la tarea correspondiente al ID proporcionado y establece su estado como completada.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = "Tarea actualizada exitosamente a completada.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Actualización de tarea a completada",
                              description = "La tarea se ha actualizado a completada.",
                              value = VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = "Tarea no encontrada.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "La tarea con el ID especificado no se encuentra.",
                              value = VALUE_ERROR_404
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = "Solicitud incorrecta.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Solicitud incorrecta",
                              description = "Revise si los parámetros requeridos están ausentes o no son válidos.",
                              value = VALUE_ERROR_400
                       )})),
      @ApiResponse(
             responseCode = "422",
             description = "No se pudo procesar la solicitud.",
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "Revise si los campos cumplen con las validaciones.",
                              value = VALUE_ERROR_422
                       )}))
   })
   @PatchMapping("/{id}/toCompleted")
   public ResponseEntity<TaskDto> updateTaskToCompleted(@PathVariable("id") Long id) {
      TaskDto updatedCompleted = service.updateTaskToCompleted(id);
      return new ResponseEntity<>(updatedCompleted, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * deleteTask: Elimina una tarea según su ID.
    *
    * @param id ID de la tarea a eliminar.
    * @return Respuesta vacía. Devolverá la petición si es correcta un (HttpStatus.OK) código 200.
    * @apiNote Elimina la tarea correspondiente al ID proporcionado.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "204",
             description = "Tarea eliminada exitosamente.",
             content = @Content(
                    mediaType = "application/json",
                    examples = {
                       @ExampleObject(
                              name = "Tarea eliminada",
                              description = "La tarea se ha eliminado exitosamente."
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = "Tarea no encontrada.",
             content = @Content(
                    mediaType = "application/json",
                    examples = {
                       @ExampleObject(
                              name = "Tarea no encontrada",
                              description = "La tarea con el ID especificado no se encuentra.",
                              value = VALUE_ERROR_404
                       )}))
   })
   @DeleteMapping("/{id}")
   public ResponseEntity<TaskDto> deleteTask(@PathVariable("id") Long id) {
      service.deleteTask(id);
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
