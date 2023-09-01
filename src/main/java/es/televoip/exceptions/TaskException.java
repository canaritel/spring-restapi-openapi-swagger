package es.televoip.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class TaskException extends RuntimeException {

   final HttpStatus errorCode;
   final String errorMessage;

   public TaskException(HttpStatus errorCode, String errorMessage) {
      this.errorCode = errorCode;
      this.errorMessage = errorMessage;
   }

}
