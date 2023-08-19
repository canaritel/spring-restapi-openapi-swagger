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
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class taskControllerGet {

   // Accesos para OPENAPI Swagger3
   // http://localhost:8080/swagger-ui/index.html
   // http://localhost:8080/v3/api-docs
   //
   private final TaskService service;

   public taskControllerGet(TaskService service) {
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
          @RequestParam Sort.Direction sortOrder, @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "20") int size) {
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

}
