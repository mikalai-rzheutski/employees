package com.mastery.java.task.exception;

import com.mastery.java.task.logger.annotations.LogAllExceptionHandlers;
import com.mastery.java.task.service.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
@LogAllExceptionHandlers
public class ExceptionsHandlerController {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage notFoundException(EmployeeNotFoundException ex,
                                           HttpServletRequest request) {
        return new ErrorMessage(request.getRequestURI(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage invalidMethodArgument(MethodArgumentNotValidException ex,
                                              HttpServletRequest request) {
        Stream<String> messages = ex.getAllErrors().stream().map(error -> error.getDefaultMessage());
        return new ErrorMessage(request.getRequestURI(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                                errorMessagesToText(messages));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage constraintViolation(ConstraintViolationException ex,
                                            HttpServletRequest request) {
        Stream<String> messages = ex.getConstraintViolations().stream().map(violation -> violation.getMessage());
        return new ErrorMessage(request.getRequestURI(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                                errorMessagesToText(messages));
    }

    private String errorMessagesToText(Stream<String> messages) {
        return messages.collect(Collectors.joining(System.lineSeparator().concat(System.lineSeparator())));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage defaultException(Exception ex,
                                         HttpServletRequest request) {
        return new ErrorMessage(request.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(),
                                "Internal server error. " + System.lineSeparator() +
                                "Please, contact the administrator to solve the problem.");
    }

}

