package es.televoip.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ExceptionHandler {

   // Excepción personalizada que puedes lanzar desde tu código cuando ocurren errores específicos relacionados con tareas
   @org.springframework.web.bind.annotation.ExceptionHandler(value = {TaskException.class})
   public ResponseEntity<ErrorResponse> handleTaskException(TaskException ex) {
      HttpStatus httpStatus = ex.getErrorCode();
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), ex.getErrorMessage());
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Esta excepción se puede utilizar cuando ocurre un error de validación en los datos de entrada de la solicitud del usuario
   @org.springframework.web.bind.annotation.ExceptionHandler(ValidationException.class)
   public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), ex.getMessage());
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Esta excepción puede utilizarse cuando no se puede encontrar un recurso solicitado en la base de datos
   @org.springframework.web.bind.annotation.ExceptionHandler(EntityNotFoundException.class)
   public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
      HttpStatus httpStatus = HttpStatus.NOT_FOUND;
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), "El recurso solicitado no fue encontrado.");
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Esta excepción se produce cuando hay errores de validación en los parámetros de entrada
   @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
      List<String> errorMessages = fieldErrors.stream()
             .map(error -> error.getField() + ": " + error.getDefaultMessage())
             .collect(Collectors.toList());
      String errorMessage = "Errores de validación en los campos: " + String.join(", ", errorMessages);
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), errorMessage);
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Esta excepción se produce cuando hay errores de validación en los parámetros de entrada
   @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
   public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), "Fallo en la conversión de datos.");
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Esta excepción se puede utilizar cuando un usuario no tiene los permisos necesarios para acceder a un recurso o realizar una acción
   @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
   public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
      HttpStatus httpStatus = HttpStatus.FORBIDDEN;
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), "Acceso denegado.");
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Esta excepción ocurre cuando se produce un error de integridad de datos en la base de datos
   @org.springframework.web.bind.annotation.ExceptionHandler(DataIntegrityViolationException.class)
   public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), "Error de integridad de datos.");
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Esta excepción se utiliza para indicar que un método ha sido llamado con un argumento no válido
   @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
   public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
      HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), "Argumentos inválidos.");
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Esta excepción se utiliza cuando hay un problema con la deserialización del JSON en el cuerpo de la solicitud
   @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
      // Aquí puedes construir una respuesta personalizada con el mensaje de error adecuado
      ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Error en el formato del JSON");
      return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
   }

   // Excepciones genéricas del tipo Exception. En caso de que ocurra alguna excepción no controlada
   @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
   public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
      HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
      ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), "Ocurrió un error inesperado.");
      return new ResponseEntity<>(errorResponse, httpStatus);
   }

   // Puedes agregar más métodos para manejar otras excepciones aquí
}
