package com.mastery.java.task.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FullNameLanguageValidator.class)
@Target( ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FullNameLanguageValid {
    String message() default "Check language of the full name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
