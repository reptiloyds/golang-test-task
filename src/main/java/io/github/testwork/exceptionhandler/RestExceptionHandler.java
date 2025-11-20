package io.github.testwork.exceptionhandler;

import io.github.testwork.config.ExceptionHandlerProperties;
import io.github.testwork.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {
    private final ExceptionHandlerProperties properties;

    @ExceptionHandler({IllegalArgumentException.class,
            IllegalStateException.class,
            MethodArgumentNotValidException.class,
            HandlerMethodValidationException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.from(exception, request, ErrorCode.INVALID_STATE_OR_ARGUMENT, false));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header(HttpHeaders.RETRY_AFTER, properties.getRetryAfter().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.from(exception, request, ErrorCode.UNEXPECTED_ERROR, true));
    }
}
