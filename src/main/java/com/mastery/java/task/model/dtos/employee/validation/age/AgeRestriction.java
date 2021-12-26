package com.mastery.java.task.model.dtos.employee.validation.age;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AgeRestriction {
  String message() default "Check age";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default 0;

  int max() default Integer.MAX_VALUE;
}
