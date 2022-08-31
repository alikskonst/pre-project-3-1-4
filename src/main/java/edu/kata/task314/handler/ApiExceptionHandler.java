package edu.kata.task314.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.xml.bind.ValidationException;
import java.util.Objects;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * example:
     * {
     * "status": 400,
     * "error": "NOT_FOUND",
     * "message": "no handler found",
     * "debugMessage": "No handler found for for GET /api/getSomethingById/123"
     * }
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException exception,
                                                                   HttpHeaders httpHeaders,
                                                                   HttpStatus httpStatus,
                                                                   WebRequest webRequest) {
        return new ResponseEntity<>(new ApiError(httpStatus, "No handler found", exception), HttpStatus.NOT_FOUND);
    }

    /**
     * example:
     * {
     * "status": 400,
     * "error": "BAD_REQUEST",
     * "message": "Validation exception",
     * "errors": [
     * {
     * "object": "userRegistrationDto",
     * "field": "userId",
     * "value": null,
     * "message": "User doesn't exist"
     * },
     * {
     * "object": "userRegistrationDto",
     * "field": "userId",
     * "value": null,
     * "message": "The field cannot be null"
     * }
     * ]
     * }
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders httpHeaders,
                                                                  HttpStatus httpStatus,
                                                                  WebRequest webRequest) {
        ApiError apiError = new ApiError(httpStatus, "Validation exception", exception);
        apiError.addValidationFieldError(exception.getBindingResult().getFieldErrors());
        apiError.addValidationObjectError(exception.getBindingResult().getAllErrors());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * example:
     * {
     * "status": 400,
     * "error": "BAD_REQUEST",
     * "message": "Malformed JSON request"
     * }
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception,
                                                                  HttpHeaders httpHeaders,
                                                                  HttpStatus httpStatus,
                                                                  WebRequest webRequest) {
        ApiError apiError = new ApiError(httpStatus, "Malformed JSON request", exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception,
                                                                      WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(
                String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
                        exception.getName(),
                        exception.getValue(),
                        Objects.requireNonNull(exception.getRequiredType()).getSimpleName())
        );
        // apiError.setDebugMessage(methodArgumentTypeMismatchException.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * example:
     * {
     * "status": 404,
     * "error": "NOT_FOUND",
     * "message": "Entity not found exception"
     * }
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception,
                                                                   WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EntityExistsException exception,
                                                                   WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(ValidationException exception,
                                                                   WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Validation exception", exception);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<Object> handleEntityNotFoundException(EmptyResultDataAccessException exception,
                                                                   WebRequest webRequest) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, "Empty result (from outside database)", exception);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", exception);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}