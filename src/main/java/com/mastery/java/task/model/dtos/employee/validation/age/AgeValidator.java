package com.mastery.java.task.model.dtos.employee.validation.age;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<AgeRestriction, LocalDate> {

  private int min;

  private int max;

  @Override
  public void initialize(AgeRestriction constraintAnnotation) {
    min = constraintAnnotation.min();
    max = constraintAnnotation.max();
  }

  @Override
  public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext context) {
    int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    return min <= age && age <= max;
  }
}
