package es.televoip.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataException extends RuntimeException {

   final HttpStatus errorCode;
   final String errorMessage;

   public DataException(HttpStatus errorCode, String errorMessage) {
      this.errorCode = errorCode;
      this.errorMessage = errorMessage;
   }

}
