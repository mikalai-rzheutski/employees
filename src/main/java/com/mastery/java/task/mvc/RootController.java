package com.mastery.java.task.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

  @GetMapping("/about")
  public String about() {
    return "about";
  }

  @GetMapping("/loggers")
  public String loggers() {
    return "loggers";
  }
}
