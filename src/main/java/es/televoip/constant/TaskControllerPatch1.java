package es.televoip.constant;

import es.televoip.constant.HttpValues;
import es.televoip.model.dto.TaskDto;
import es.televoip.model.enums.TaskStatus;
import es.televoip.service.TaskService;
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
public class TaskControllerPatch1 {

   // Accesos para OPENAPI Swagger3
   // http://localhost:8080/swagger-ui/index.html
   // http://localhost:8080/v3/api-docs
   //
   private final TaskService service;

   public TaskControllerPatch1(TaskService service) {
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
                              value = HttpValues.VALUE_OK
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
                              value = HttpValues.VALUE_ERROR_404
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
                              value = HttpValues.VALUE_ERROR_400
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
                              value = HttpValues.VALUE_ERROR_422
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
                              value = HttpValues.VALUE_OK
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
                              value = HttpValues.VALUE_ERROR_404
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
                              value = HttpValues.VALUE_ERROR_400
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
                              value = HttpValues.VALUE_ERROR_422
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
                              value = HttpValues.VALUE_OK
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
                              value = HttpValues.VALUE_ERROR_404
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
                              value = HttpValues.VALUE_ERROR_400
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
                              value = HttpValues.VALUE_ERROR_422
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
                              value = HttpValues.VALUE_OK
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
                              value = HttpValues.VALUE_ERROR_404
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
                              value = HttpValues.VALUE_ERROR_400
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
                              value = HttpValues.VALUE_ERROR_422
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
                              value = HttpValues.VALUE_OK
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
                              value = HttpValues.VALUE_ERROR_404
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
                              value = HttpValues.VALUE_ERROR_400
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
                              value = HttpValues.VALUE_ERROR_422
                       )}))
   })
   @PatchMapping("/{id}/toCompleted")
   public ResponseEntity<TaskDto> updateTaskToCompleted(@PathVariable("id") Long id) {
      TaskDto updatedCompleted = service.updateTaskToCompleted(id);
      return new ResponseEntity<>(updatedCompleted, HttpStatus.OK);
   }

}