package es.televoip.controller;

import es.televoip.constant.TaskConstant;
import es.televoip.model.dto.TaskDto;
import es.televoip.service.TaskService;
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
   private final TaskService service;

   public TaskControllerPostAndDelete(TaskService service) {
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
                              value = TaskConstant.VALUE_OK
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
                              value = TaskConstant.VALUE_ERROR_422
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
                              value = TaskConstant.VALUE_ERROR_400
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
                              value = TaskConstant.VALUE_ERROR_404
                       )}))
   })
   @DeleteMapping("/{id}")
   public ResponseEntity<TaskDto> deleteTask(@PathVariable("id") Long id) {
      service.deleteTask(id);
      return new ResponseEntity<>(HttpStatus.OK);
   }

}
