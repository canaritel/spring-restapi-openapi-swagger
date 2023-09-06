package es.televoip.controller;

import es.televoip.constant.TaskConstant;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskStatus;
import es.televoip.service.implement.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskControllerPatch {

   // Accesos para OPENAPI Swagger3
   // http://localhost:8080/swagger-ui/index.html
   // http://localhost:8080/v3/api-docs
   //
   private final TaskServiceImpl service;

   public TaskControllerPatch(TaskServiceImpl service) {
      this.service = service;
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
   @Operation(summary = "Update a task")
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = TaskConstant.TASK_UPDATED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_UPDATED,
                              description = TaskConstant.TASK_DESCRIPTION_UPDATE,
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
                       )}))
   })
   @PutMapping("/{id}")
   public ResponseEntity<TaskDto> updateTask(@Parameter(description = "id of task to be updated") @PathVariable Long id,
          @Valid @RequestBody TaskDto taskDto) {
      // Valid debe tener anotaciones de validación adecuadas, como @NotBlank, @NotNull, @Size, etc., en su capa DTO
      TaskDto updatedTask = service.update(id, taskDto);
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
   @Operation(summary = "Update task's date of finished")
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = TaskConstant.TASK_DATE_UPDATED_SUCCESS,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_DATE_UPDATED,
                              description = TaskConstant.TASK_DESCRIPTION_DATE_FINISH,
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
                       )}))
   })
   @PatchMapping("/{id}/dateOfFinished")
   public ResponseEntity<TaskDto> updateTaskDateOfFinished(@Parameter(description = "id of task to be updated") @PathVariable Long id,
          @Parameter(description = "date of task to be updated") @RequestParam LocalDateTime newDateOfFinished) {
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
   @Operation(summary = "Update task's status")
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = TaskConstant.TASK_STATE_UPDATED,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_STATE_UPDATED,
                              description = TaskConstant.TASK_DESCRIPTION_STATUS,
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
                       )}))
   })
   @PatchMapping("/{id}/status")
   public ResponseEntity<TaskDto> updateTaskStatus(@Parameter(description = "id of task to be updated") @PathVariable Long id,
          @Parameter(description = "status of task to be updated") @RequestParam TaskStatus taskStatus) {
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
   @Operation(summary = "Update task's completion")
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = TaskConstant.TASK_IS_COMPLETED,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_COMPLETED,
                              description = TaskConstant.TASK_DESCRIPTION_IS_COMPLETED,
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
                       )}))
   })
   @PatchMapping("/{id}/isCompleted")
   public ResponseEntity<TaskDto> updateTaskIsCompleted(@Parameter(description = "id of task to be updated") @PathVariable Long id,
          @Parameter(description = "boolean completed of task to be updated") @RequestParam Boolean isCompleted) {
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
   @Operation(summary = "Update task to completed")
   @ApiResponses(value = {
      @ApiResponse(
             responseCode = "200",
             description = TaskConstant.TASK_UPDATED_TO_COMPLETED,
             content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TaskDto.class),
                    examples = {
                       @ExampleObject(
                              name = TaskConstant.TASK_UPDATED_COMPLETED,
                              description = TaskConstant.TASK_UPDATED_TO_COMPLETED,
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
                       )}))
   })
   @PatchMapping("/{id}/toCompleted")
   public ResponseEntity<TaskDto> updateTaskToCompleted(@Parameter(description = "id of task to be updated as completed")
          @PathVariable("id") Long id) {
      TaskDto updatedCompleted = service.markTaskAsCompleted(id);
      return new ResponseEntity<>(updatedCompleted, HttpStatus.OK);
   }

}
