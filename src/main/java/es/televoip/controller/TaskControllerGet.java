package es.televoip.controller;

import es.televoip.constant.TaskConstant;
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
public class TaskControllerGet {

   // Accesos para OPENAPI Swagger3
   // http://localhost:8080/swagger-ui/index.html
   // http://localhost:8080/v3/api-docs
   //
   private final TaskService service;

   public TaskControllerGet(TaskService service) {
      this.service = service;
   }

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
             description = TaskConstant.TASK_RETRIEVED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_GET,
                              description = TaskConstant.TASK_GET_SUCCESS,
                              value = TaskConstant.VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = TaskConstant.TASK_NOT_FOUND,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_NOT_FOUND,
                              description = TaskConstant.TASK_ID_SPECIFIC_NOT_FOUND,
                              value = TaskConstant.VALUE_ERROR_404
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = TaskConstant.TASK_INVALID_REQUEST,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_INVALID_REQUEST,
                              description = TaskConstant.TASK_PARAM_NOT_VALID,
                              value = TaskConstant.VALUE_ERROR_400
                       )}))
   })
   @GetMapping("/{id}")
   public ResponseEntity<TaskDto> getTask(@PathVariable("id") Long id) {
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
             description = TaskConstant.TASK_RETRIEVED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_GET_ALL,
                              description = TaskConstant.TASK_GET_ALL_SUCCESS,
                              value = TaskConstant.VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = TaskConstant.TASK_INVALID_REQUEST,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_INVALID_REQUEST,
                              description = TaskConstant.TASK_PARAM_NOT_VALID,
                              value = TaskConstant.VALUE_ERROR_400
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
             description = TaskConstant.TASK_RETRIEVED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_GET_ALL_SORTED,
                              description = TaskConstant.TASK_GET_ALL_SORTED_SUCCESS,
                              value = TaskConstant.VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = TaskConstant.TASK_INVALID_REQUEST,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_INVALID_REQUEST,
                              description = TaskConstant.TASK_PARAM_NOT_VALID,
                              value = TaskConstant.VALUE_ERROR_400
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
             description = TaskConstant.TASK_RETRIEVED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_GET_ALL_SORTED_PAGED,
                              description = TaskConstant.TASK_GET_ALL_SORTED_PAGED_SUCCESS,
                              value = TaskConstant.VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = TaskConstant.TASK_INVALID_REQUEST,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_INVALID_REQUEST,
                              description = TaskConstant.TASK_PARAM_NOT_VALID,
                              value = TaskConstant.VALUE_ERROR_400
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
             description = TaskConstant.TASK_GET_ALL_STATUS_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_GET_ALL_STATUS_NAME,
                              description = TaskConstant.TASK_GET_ALL_STATUS_RESULT_DESCRIPTION,
                              value = TaskConstant.VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = TaskConstant.TASK_INVALID_REQUEST,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_INVALID_REQUEST,
                              description = TaskConstant.TASK_PARAM_NOT_VALID,
                              value = TaskConstant.VALUE_ERROR_400
                       )}))
   })
   @GetMapping("/status/{status}")
   public ResponseEntity<List<TaskDto>> getAllByTaskStatus(@PathVariable("status") TaskStatus status) {
      List<TaskDto> tasks = service.getAllByTaskStatus(status);
      return new ResponseEntity<>(tasks, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * Obtiene todas las tareas según su estado de completitud.
    *
    * @param isCompleted Estado de completitud de las tareas a obtener.
    * @return Una respuesta que contiene la lista de objetos tarea correspondientes al estado de completitud.
    * @apiNote Este endpoint devuelve una lista de todas las tareas que coinciden con el estado de completitud proporcionado.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = TaskConstant.TASK_GET_ALL_COMPLETED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_GET_ALL_COMPLETED_NAME,
                              description = TaskConstant.TASK_GET_ALL_COMPLETED_DESCRIPTION,
                              value = TaskConstant.VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = TaskConstant.TASK_INVALID_REQUEST,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_INVALID_REQUEST,
                              description = TaskConstant.TASK_PARAM_NOT_VALID,
                              value = TaskConstant.VALUE_ERROR_400
                       )}))
   })
   @GetMapping("/completed/{isCompleted}")
   public ResponseEntity<List<TaskDto>> getTasksByCompletion(@PathVariable("isCompleted") Boolean isCompleted) {
      List<TaskDto> tasks = service.getAllTasksByCompletionStatus(isCompleted);
      return new ResponseEntity<>(tasks, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * findTasksByTitleContaining: Obtiene todas las tareas que contienen un título específico.
    *
    * @param title Título a buscar en las tareas.
    * @return Respuesta con la lista de objetos tarea que contienen el título proporcionado.
    * @apiNote Devuelve una lista de todas las tareas que contienen el título especificado.
    */
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = TaskConstant.TASK_GET_ALL_TITLE_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_GET_ALL_TITLE_NAME,
                              description = TaskConstant.TASK_GET_ALL_TITLE_DESCRIPTION,
                              value = TaskConstant.VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "400",
             description = TaskConstant.TASK_INVALID_REQUEST,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_INVALID_REQUEST,
                              description = TaskConstant.TASK_PARAM_NOT_VALID,
                              value = TaskConstant.VALUE_ERROR_400
                       )}))
   })
   @GetMapping("/alltitles/{title}")
   public ResponseEntity<List<TaskDto>> findTasksByTitleContaining(@PathVariable("title") String title) {
      List<TaskDto> tasks = service.getAllTasksByTitleContaining(title);
      if (tasks.isEmpty()) {
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
         return new ResponseEntity<>(tasks, HttpStatus.OK);
      }
   }

}
