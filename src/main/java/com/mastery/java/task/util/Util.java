package com.mastery.java.task.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Util {

  public static String asJsonString(Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      final String jsonContent = mapper.writeValueAsString(obj);
      return jsonContent;
    } catch (JsonProcessingException e) {
      return "Cannot generate json of " + obj.getClass().getSimpleName();
    }
  }
}
