package amzx.app.exception.handler;

import amzx.app.exception.exceptions.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
  @ExceptionHandler(UserExistException.class)
  public ResponseEntity<String> handleUserExistException(UserExistException ex)
  {
   String text = "Username is taken";
    return new ResponseEntity<>(text, HttpStatus.BAD_REQUEST);
  }


}
