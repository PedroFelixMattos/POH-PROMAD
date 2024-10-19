import com.example.poh_promad.service.ReuJaAssociadoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReuJaAssociadoException.class)
    public ResponseEntity<String> handleReuJaAssociadoException(ReuJaAssociadoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}