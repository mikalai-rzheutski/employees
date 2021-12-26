package com.mastery.java.task.model.dtos.employee.validation.fullname;

import com.mastery.java.task.model.dtos.employee.EmployeeDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class FullNameLanguageValidator
    implements ConstraintValidator<ValidFullNameLanguage, EmployeeDto> {

  private static final String BELARUSIAN = "[а-яўi&&[^щиъ]]+";

  private static final String RUSSIAN = "[а-я]+";

  private static final String ENGLISH = "[a-z]+";

  @Override
  public boolean isValid(EmployeeDto employeeDto, ConstraintValidatorContext context) {
    String fullName =
        Optional.ofNullable(employeeDto.getFirstName())
            .orElse("")
            .concat(Optional.ofNullable(employeeDto.getLastName()).orElse(""))
            .toLowerCase()
            .replaceAll("\\s+", "")
            .replaceAll("-", "");
    return fullName.matches(BELARUSIAN) || fullName.matches(RUSSIAN) || fullName.matches(ENGLISH);
  }
}
