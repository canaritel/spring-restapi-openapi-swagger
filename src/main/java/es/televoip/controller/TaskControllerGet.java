package es.televoip.controller;

import es.televoip.constant.TaskConstant;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.SortField;

import es.televoip.model.enums.TaskStatus;
import es.televoip.service.implement.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
   private final TaskServiceImpl service;

   public TaskControllerGet(TaskServiceImpl service) {
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
   @Operation(summary = "Get a book by its id")
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
      TaskDto taskDto = service.findById(id);
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
   @Operation(summary = "Get all tasks")
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
      List<TaskDto> tasks = service.findAll();
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
   @Operation(summary = "Get all tasks sorted")
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
   public ResponseEntity<List<TaskDto>> getAllTasksSorted(@RequestParam SortField sortBy,
          @RequestParam Sort.Direction sortOrder) {
      List<TaskDto> tasks = service.findAllSorted(sortBy, sortOrder);
      return new ResponseEntity<>(tasks, HttpStatus.OK);
   }

   // ********************************************************************************************************
   /**
    * getAllTasksSortedAndPaginated: Obtiene todas las tareas ordenadas y paginadas.
    *
    * @param sortBy Campo por el cual se ordenarán las tareas.
    * @param sortOrder Dirección de la ordenación (ASCENDENTE o DESCENDENTE).
    * @param pageable Objeto Page con el tamaño y página
    * @return Respuesta con la lista de objetos tarea ordenada y paginada.
    * @apiNote Devuelve una lista de todas las tareas ordenadas y paginadas según los parámetros proporcionados.
    */
   @Operation(summary = "Get all tasks sorted and paginated")
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
   public ResponseEntity<Page<TaskDto>> getAllTasksSortedAndPaginated(@RequestParam SortField sortBy,
          @RequestParam Sort.Direction sortOrder, @RequestParam Pageable pageable) {
      Page<TaskDto> taskPage = service.findAllSortedAndPaged(sortBy, sortOrder, pageable);
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
   @Operation(summary = "Get tasks by status")
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
      List<TaskDto> tasks = service.getTasksByTaskStatus(status);
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
   @Operation(summary = "Get tasks by completion")
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
      List<TaskDto> tasks = service.getTasksByCompletion(isCompleted);
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
   @Operation(summary = "Find tasks by title containing")
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
      List<TaskDto> tasks = service.getTasksByTitleContaining(title);
      if (tasks.isEmpty()) {
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } else {
         return new ResponseEntity<>(tasks, HttpStatus.OK);
      }
   }

}
