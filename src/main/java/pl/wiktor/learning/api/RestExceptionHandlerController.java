package pl.wiktor.learning.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.wiktor.learning.api.exception.DomainException;

import java.time.Clock;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandlerController {
    public static final String INVALID_DATA = "Niepoprawne dane";
    public static final String INVALID_VALIDATION = "Błędna walidacja";
    public static final String INTERNAL_SERVER_ERROR = "Błąd serwera";

    private final DateTimeFormatter formatter;
    private final Clock clock;

    public RestExceptionHandlerController(DateTimeFormatter formatter, Clock clock) {
        this.formatter = formatter;
        this.clock = clock;
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Error> handleDomainException(DomainException exception) {
        exception.printStackTrace();

        HttpStatus httpStatus = HttpStatus.valueOf(exception.getCode().getStatus());
        Error body = new Error(httpStatus.value(), INVALID_DATA, formatter.format(clock.instant()));
        body.addFieldError(exception.getMessage());

        return new ResponseEntity<>(body, httpStatus);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Error> handleException(MissingServletRequestParameterException exception){
        exception.printStackTrace();

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Error body = new Error(httpStatus.value(), INVALID_DATA, formatter.format(clock.instant()));
        body.addFieldError(exception.getParameterName(), exception.getMessage());

        return new ResponseEntity<>(body, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception exception){
        exception.printStackTrace();

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        Error body = new Error(httpStatus.value(), INTERNAL_SERVER_ERROR, formatter.format(clock.instant()));
        body.addFieldError(exception.getMessage());

        return new ResponseEntity<>(body, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        exception.printStackTrace();

        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return new ResponseEntity<>(processFieldErrors(fieldErrors), HttpStatus.BAD_REQUEST);
    }

    private Error processFieldErrors(List<FieldError> fieldErrors) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), INVALID_VALIDATION, formatter.format(clock.instant()));
        for (FieldError fieldError: fieldErrors) {
            error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return error;
    }

    static class Error {
        private final int status;
        private final String message;
        private final String timestamp;
        private final List<InnerError> errors;

        Error(int status, String message, String timestamp) {
            this.status = status;
            this.message = message;
            this.timestamp = timestamp;
            this.errors = new ArrayList<>();
        }

        public int getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void addFieldError(String field, String message) {
            InnerError innerError = new InnerError(message, field);
            errors.add(innerError);
        }

        public void addFieldError(String message) {
            InnerError innerError = new InnerError(message);
            errors.add(innerError);
        }

        public List<InnerError> getErrors() {
            return errors;
        }

        static class InnerError{
            private final String message;
            private String field;

            public InnerError(String message, String field) {
                this.message = message;
                this.field = field;
            }

            public InnerError(String message) {
                this.message = message;
            }

            public String getMessage() {
                return message;
            }

            public String getField() {
                return field;
            }
        }
    }
}
