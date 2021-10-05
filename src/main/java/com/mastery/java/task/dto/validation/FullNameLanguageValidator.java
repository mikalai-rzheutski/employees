package com.mastery.java.task.dto.validation;

import com.mastery.java.task.dto.Employee;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class FullNameLanguageValidator implements ConstraintValidator<FullNameLanguageValid, Employee> {
    @Override
    public boolean isValid(Employee employee, ConstraintValidatorContext constraintValidatorContext) {
        String fullName = Optional.ofNullable(employee.getFirstName()).orElse("").
                concat(Optional.ofNullable(employee.getLastName()).orElse("")).toLowerCase();
        String byRegexp = "^[а-яўi&&[^щиъ]]+$";
        String ruRegexp = "^[а-я]+$";
        String enRegexp = "^[a-z]+$";
        return fullName.matches(byRegexp) || fullName.matches(ruRegexp) || fullName.matches(enRegexp);
    }
}
