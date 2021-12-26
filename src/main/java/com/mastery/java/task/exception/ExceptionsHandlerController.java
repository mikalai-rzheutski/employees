package com.mastery.java.task.exception;

import com.mastery.java.task.service.EntityNotFoundException;
import com.mastery.java.task.util.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
@Log4j2
public class ExceptionsHandlerController {

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public ErrorMessage notFoundException(EntityNotFoundException ex, HttpServletRequest request) {
    ErrorMessage em =
        new ErrorMessage(
            request.getRequestURI(),
            HttpStatus.NOT_FOUND.value(),
            LocalDateTime.now(),
            ex.getMessage());
    logException(ex, em);
    return em;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage invalidMethodArgument(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    Stream<String> messages = ex.getAllErrors().stream().map(error -> error.getDefaultMessage());
    ErrorMessage em =
        new ErrorMessage(
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            errorMessagesToText(messages));
    logException(ex, em);
    return em;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage constraintViolation(
      ConstraintViolationException ex, HttpServletRequest request) {
    Stream<String> messages =
        ex.getConstraintViolations().stream().map(violation -> violation.getMessage());
    ErrorMessage em =
        new ErrorMessage(
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            errorMessagesToText(messages));
    logException(ex, em);
    return em;
  }

  @ExceptionHandler(InvalidDataAccessApiUsageException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorMessage invalidearchParameter(
      InvalidDataAccessApiUsageException ex, HttpServletRequest request) {
    ErrorMessage em =
        new ErrorMessage(
            request.getRequestURI(),
            HttpStatus.BAD_REQUEST.value(),
            LocalDateTime.now(),
            ex.getMessage());
    logException(ex, em);
    return em;
  }

  private String errorMessagesToText(Stream<String> messages) {
    return messages.collect(
        Collectors.joining(System.lineSeparator().concat(System.lineSeparator())));
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorMessage defaultException(Exception ex, HttpServletRequest request) {
    ErrorMessage em =
        new ErrorMessage(
            request.getRequestURI(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            LocalDateTime.now(),
            "Internal server error. "
                + System.lineSeparator()
                + "Please, contact the administrator to solve the problem.");
    logException(ex, em);
    return em;
  }

  private void logException(Exception e, ErrorMessage em) {
    log.error(
        "EXCEPTION OCCURRED. Error message: {}" + System.lineSeparator() + "{}",
        () -> Util.asJsonString(em),
        () -> exceptionStackTraceToString(e));
  }

  private String exceptionStackTraceToString(Exception e) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    return sw.toString();
  }
}
