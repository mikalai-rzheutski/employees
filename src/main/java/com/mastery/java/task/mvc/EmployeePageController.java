package com.mastery.java.task.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/employees")
public class EmployeePageController {

  @GetMapping
  public ModelAndView showListOfAllEmployees() {
    return new ModelAndView("allEmployees");
  }

  @GetMapping("/{id}")
  public ModelAndView editEmployee(@PathVariable("id") int id) {
    ModelAndView model = new ModelAndView("editEmployee");
    model.addObject("id", id);
    return model;
  }

  @GetMapping("/new/")
  public ModelAndView createEmployee() {
    return new ModelAndView("createEmployee");
  }
}
