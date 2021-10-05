package com.mastery.java.task.rest.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public  ErrorMessage notFoundException(NotFoundException ex, HttpServletRequest request) {
        log.warn("NotFoundException occurred: {}", ex.getMessage());
        return new ErrorMessage(request.getRequestURI(), HttpStatus.NOT_FOUND.value(), new Date(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public  ErrorMessage invalidParameterException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        log.warn("MethodArgumentNotValidException occurred: {}", ex.getMessage());
        String text = ex.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce((res, m) -> res + System.lineSeparator() + m).get();
        return new ErrorMessage(request.getRequestURI(), HttpStatus.BAD_REQUEST.value(), new Date(), text);
    }
}

