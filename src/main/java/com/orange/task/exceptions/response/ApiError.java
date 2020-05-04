package com.orange.task.exceptions.response;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ApiError {

    private Date timestamp;
    private String path;
    private HttpMethod httpMethod;
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(Date timestamp,
                    String path,
                    HttpMethod httpMethod,
                    HttpStatus status,
                    String message,
                    List<String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
        this.httpMethod = httpMethod;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(Date timestamp,
                    String path,
                    HttpMethod httpMethod,
                    HttpStatus status,
                    String message,
                    String error) {
        this.timestamp = timestamp;
        this.status = status;
        this.path = path;
        this.httpMethod = httpMethod;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApiError)) return false;
        ApiError apiError = (ApiError) o;
        return getTimestamp().equals(apiError.getTimestamp()) &&
                getPath().equals(apiError.getPath()) &&
                getHttpMethod() == apiError.getHttpMethod() &&
                getStatus() == apiError.getStatus() &&
                getMessage().equals(apiError.getMessage()) &&
                getErrors().equals(apiError.getErrors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTimestamp(), getPath(), getHttpMethod(), getStatus(), getMessage(), getErrors());
    }
}
