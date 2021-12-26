package com.mastery.java.task.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Schema(name = "Error message")
public class ErrorMessage {
  private String uri;

  private int status;

  @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss.SSS")
  private LocalDateTime timestamp;

  private String message;
}
