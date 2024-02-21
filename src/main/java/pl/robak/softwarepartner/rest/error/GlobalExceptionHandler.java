package pl.robak.softwarepartner.rest.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        Logger.getLogger("controller").log(Level.WARNING, ex, ex::getMessage);
        return ResponseEntity.internalServerError().body("An error occurred");
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex) {
        Logger.getLogger("controller").log(Level.WARNING, ex, ex::getMessage);
        return ResponseEntity.notFound().build();
    }
}
