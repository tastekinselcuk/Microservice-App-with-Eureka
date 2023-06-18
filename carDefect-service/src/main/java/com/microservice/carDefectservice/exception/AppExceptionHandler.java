package com.microservice.carDefectservice.exception;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class AppExceptionHandler {
	
    /**
     * Handle not supported http method exception.
     *
     * @param req request info.
     * @param e exception info.
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> httpRequestMethodNotSupported(HttpServletRequest req, HttpRequestMethodNotSupportedException e) {
        StringBuilder builder = new StringBuilder();
        builder.append(e.getMethod());
        builder.append(" method is not supported for request: " +
                e.getMethod() + " " + req.getRequestURI() +
                ". Supported methods are: ");
        e.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

        log.debug(builder.toString());

        GenericResponse<?> response = GenericResponse.createErrorResponse()
                .error("Method Not Supported")
                .errorDescription(builder.toString());

        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Handle username not found exception.
     *
     * @param e exception info.
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> usernameNotFoundException(UsernameNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle access denied exception.
     *
     * @param req request info.
     * @param e exception info.
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> accessDeniedException(HttpServletRequest req, AccessDeniedException e) {
        log.debug("User: " +
                SecurityContextHolder.getContext().getAuthentication().getName() +
                " tried to access to: " + req.getRequestURI() +
                " but has no proper role.");

        GenericResponse<?> response = GenericResponse.createErrorResponse()
                .error("Access Denied")
                .errorDescription(e.getLocalizedMessage());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle no handler found for requested link.
     *
     * @param req request info.
     * @param e exception info.
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> noHandlerFoundException(HttpServletRequest req, NoHandlerFoundException e) {
        String message = "No handler found for " + e.getHttpMethod() + " " + e.getRequestURL();

        log.debug(message);

        GenericResponse<?> response = GenericResponse.createErrorResponse()
                .error("No Handler Found")
                .errorDescription(message);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle method argument type mismatch exception.
     *
     * @param e exception info.
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = e.getName() + " shold be of type " + e.getRequiredType().getName();

        log.debug("MethodArgumentTypeMismatchException: " + message);

        GenericResponse<?> response = GenericResponse.createErrorResponse()
                .error("Method Argument Mismatch")
                .errorDescription(message);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle constraint violation exception.
     * Report the result of constraint violations.
     *
     * @param e exception info.
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(HttpServletRequest req, ConstraintViolationException e) {
        List<String> errors = new ArrayList<String>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
              violation.getPropertyPath() + ": " + violation.getMessage());
        }

        log.debug("Constraint violation at " + req.getRequestURI() + ". "
                + e.getLocalizedMessage() + " - data: " + errors);

        GenericResponse<?> response = GenericResponse.createErrorResponse(errors)
                .error("Constraint Violation")
                .errorDescription(e.getLocalizedMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Error handle for @Valid.
     *
     * @param e exception info.
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        log.debug("@Valid exception at " + req.getRequestURI() + ". Error: "
                + e.getLocalizedMessage() + ". Data: " + errors);

        GenericResponse<?> response = GenericResponse.createErrorResponse(errors)
                .error("Validation Exception")
                .errorDescription(e.getLocalizedMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for {@link InvisoException}.
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> customException(HttpServletRequest req, AppException e) {
        log.debug(e.getLogMessage());

        GenericResponse<?> response = GenericResponse.createErrorResponse()
                .error(e.getTitle())
                .errorDescription(e.getMessage());

        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    /**
     * Handle all other exceptions that are not defined above.
     *
     * @param req request info.
     * @param e exception info.
     * @return {@link ResponseEntity}.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(HttpServletRequest req, Exception e) {
        GenericResponse<?> response = GenericResponse.createErrorResponse()
                .error("Error")
                .errorDescription(e.getLocalizedMessage() + "occured. Caused by: " + e.getClass());

        log.error("Unknown exception at " + req.getRequestURI() + ". Error: "
                + e.getLocalizedMessage() + "occured. Caused by: " + e.getClass());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
