package es.televoip.controller;

import es.televoip.constant.TaskConstant;
import es.televoip.model.dto.TaskDto;
import es.televoip.service.implement.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskControllerPostAndDelete {

   // Accesos para OPENAPI Swagger3
   // http://localhost:8080/swagger-ui/index.html
   // http://localhost:8080/v3/api-docs
   //
   private final TaskServiceImpl service;

   public TaskControllerPostAndDelete(TaskServiceImpl service) {
      this.service = service;
   }

   // ********************************************************************************************************
   /**
    * createTask: Crea una nueva tarea.
    *
    * @param taskDto Datos de la tarea a crear.
    * @return Respuesta con la tarea creada.
    * @apiNote Crea una nueva tarea con los datos proporcionados en el cuerpo de la solicitud.
    */
   @Operation(summary = "Create a new task")
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "201",
             description = TaskConstant.TASK_CREATED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_SUCCESS_CREATION,
                              description = TaskConstant.TASK_DESCRIPTION_UPDATE,
                              value = TaskConstant.VALUE_OK
                       )})),
      @ApiResponse(
             responseCode = "422",
             description = TaskConstant.TASK_REQUEST_NOT_PROCESSED,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_NOT_FOUND,
                              description = TaskConstant.TASK_RECORD_CHECK_VALID,
                              value = TaskConstant.VALUE_ERROR_422
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
   @PostMapping
   public ResponseEntity<TaskDto> createTask(@Valid @RequestBody TaskDto taskDto) {
      // Valid debe tener anotaciones de validación adecuadas, como @NotBlank, @NotNull, @Size, etc., en su capa DTO
      TaskDto createdTask = service.saveTask(taskDto);
      return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
   }

   // ********************************************************************************************************
   /**
    * deleteTask: Elimina una tarea según su ID.
    *
    * @param id ID de la tarea a eliminar.
    * @return Respuesta vacía. Devolverá la petición si es correcta un (HttpStatus.OK) código 200.
    * @apiNote Elimina la tarea correspondiente al ID proporcionado.
    */
   @Operation(summary = "Delete a task by ID")
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "204",
             description = TaskConstant.TASK_DELETED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_DELETED,
                              description = TaskConstant.TASK_DELETED_SUCCESS
                       )})),
      @ApiResponse(
             responseCode = "404",
             description = TaskConstant.TASK_NOT_FOUND,
             content = @Content(
                    mediaType = "application/json",
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_NOT_FOUND,
                              description = TaskConstant.TASK_ID_SPECIFIC_NOT_FOUND,
                              value = TaskConstant.VALUE_ERROR_404
                       )}))
   })
   @DeleteMapping("/{id}")
   public ResponseEntity<TaskDto> deleteTask(@Parameter(description = "id of task to be deleted") @PathVariable("id") Long id) {
      if (service.getTaskById(id) == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      service.deleteTaskById(id);
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
