package io.github.testwork.dto;

import io.github.testwork.exceptionhandler.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String code;
    private String message;
    private boolean retryable;
    private String path;

    private ErrorResponse(LocalDateTime timestamp, String code, String message, boolean retryable, String path) {
        this.timestamp = timestamp;
        this.code = code;
        this.message = message;
        this.retryable = retryable;
        this.path = path;
    }

    public static ErrorResponse from(Exception exception, HttpServletRequest request, ErrorCode errorCode, boolean retryable) {
        return new ErrorResponse(LocalDateTime.now(), errorCode.toString(), exception.getMessage(), retryable, request.getRequestURI());
    }
}
