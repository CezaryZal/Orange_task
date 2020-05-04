package com.orange.task.exceptions;

import com.orange.task.exceptions.invalid.InvalidCalendarListException;
import com.orange.task.exceptions.response.ApiError;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(
                new Date(),
                request.getDescription(false),
                ((ServletWebRequest) request).getHttpMethod(),
                HttpStatus.BAD_REQUEST,
                ex.getLocalizedMessage(),
                errors);

        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @ExceptionHandler({InvalidCalendarListException.class})
    protected ResponseEntity<Object> handleValidationMaximumNumberAndNonOverlappingIdExceptions(
            Exception ex, WebRequest request) {

        ApiError apiError = createApiError(ex, request, HttpStatus.BAD_REQUEST);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    private ApiError createApiError(Exception ex, WebRequest request, HttpStatus status){
        return new ApiError(
                new Date(),
                request.getDescription(false),
                ((ServletWebRequest) request).getHttpMethod(),
                status,
                ex.getLocalizedMessage(),
                ex.getMessage());
    }
}
