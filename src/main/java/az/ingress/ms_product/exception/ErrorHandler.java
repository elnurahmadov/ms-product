package az.ingress.ms_product.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static az.ingress.ms_product.exception.ExceptionConstants.UNEXPECTED_EXCEPTION_CODE;
import static az.ingress.ms_product.exception.ExceptionConstants.UNEXPECTED_EXCEPTION_MESSAGE;
import static az.ingress.ms_product.exception.ExceptionConstants.VALIDATION_ERROR_CODE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception exception) {
        log.error("Exception: ", exception);
        return new ExceptionResponse(UNEXPECTED_EXCEPTION_CODE, UNEXPECTED_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handle(NotFoundException exception) {
        log.error("NotFoundException: ", exception);
        return new ExceptionResponse(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handle(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException: ", exception);
        return ExceptionResponse.builder()
                .code(VALIDATION_ERROR_CODE)
                .message(exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(e -> e.getField() + ": " + e.getDefaultMessage())
                        .collect(Collectors.joining(", ")))
                .build();
    }

    @ExceptionHandler(CustomFeignException.class)
    public ResponseEntity<ExceptionResponse> handle(CustomFeignException exception) {
        log.error("CustomFeignException: param={}", exception.getMessage());

        return ResponseEntity.status(exception.getStatus()).body(
                ExceptionResponse.builder()
                        .message(exception.getMessage())
                        .code(exception.getCode())
                        .build()
        );

    }
}
