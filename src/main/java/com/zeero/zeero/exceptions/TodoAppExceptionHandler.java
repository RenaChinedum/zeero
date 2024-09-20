package com.zeero.zeero.exceptions;

import com.zeero.zeero.dto.response.UnifiedResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
public class TodoAppExceptionHandler {

    @ExceptionHandler(TodoAppException.class)
    public ResponseEntity<UnifiedResponse<?>> customExceptionHandle(TodoAppException ex) {
        ErrorDto errorDto = new ErrorDto(ex.getErrorCode(), ex.getMessage());
        return new ResponseEntity<>(generateUnifiedResponse(errorDto), HttpStatusCode.valueOf(ex.getErrorCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder builder = new StringBuilder();
        result.getFieldErrors().forEach(error -> {
            builder.append(error.getField()).append(": ").append(error.getDefaultMessage());
        });
        ErrorDto errorDto = new ErrorDto(400, builder.toString());
        return ResponseEntity.badRequest().body(generateUnifiedResponse(errorDto));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex) {
        String error = ex.getParameterName() + " parameter is missing";
        ErrorDto errorDto = new ErrorDto(404, error);
        return new ResponseEntity<>(generateUnifiedResponse(errorDto), HttpStatusCode.valueOf(404));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnifiedResponse<List<String>> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
        String message = "Bad Request";
        return generateUnifiedResponse(errors, message);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + Objects.requireNonNull(ex.getRequiredType()).getName();
        ErrorDto errorDto = new ErrorDto(400, error);
        return new ResponseEntity<>(generateUnifiedResponse(errorDto), HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifiedResponse<?> handleAll(Exception ex) {
        String message = "Internal Server Error";
        ErrorDto errorDto = new ErrorDto(500, message);
        return generateUnifiedResponse(errorDto);
    }

    public <T> UnifiedResponse<T> generateUnifiedResponse(T data, String exMessage) {
        UnifiedResponse<T> response = new UnifiedResponse<>();
        response.setMessage(exMessage);
        response.setHasError(true);
        response.setData(data);
        return response;
    }

    public <T> UnifiedResponse<T> generateUnifiedResponse(T data) {
        UnifiedResponse<T> response = new UnifiedResponse<>();
        response.setMessage("failed");
        response.setHasError(true);
        response.setData(data);
        return response;
    }

}
