package com.mastery.java.task.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ErrorMessage {
    private String uri;
    private int status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private String message;
}
