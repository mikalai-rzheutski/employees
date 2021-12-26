package com.mastery.java.task.model.dtos.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.dtos.employee.validation.age.AgeRestriction;
import com.mastery.java.task.model.dtos.employee.validation.fullname.ValidFullNameLanguage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ValidFullNameLanguage(
    message =
        "The first and last names should use one language only (Belarusian, Russian and English are allowed)")
@Schema(name = "Employee")
public class EmployeeDto {

  private static final String WRONG_NAME_MSG =
      "It should consist of one or several words separated by whitespaces or hyphens. "
          + "Every word should start with a capital letter. "
          + "Only letters (both cyrillic and latin) are allowed.";

  private static final String REDUNDANT_WHITESPACES_MSG = "Delete redundant whitespaces in the ";

  @Schema(description = "The employee ID")
  private Integer id;

  @Schema(description = "The employee's first name")
  @Pattern(
      regexp = "\\s*\\p{Lu}\\p{Ll}*([\\s-]\\p{Lu}\\p{Ll}*)*\\s*",
      message = "Check the First Name. " + WRONG_NAME_MSG)
  @Pattern(regexp = "\\S(.*\\S)*", message = REDUNDANT_WHITESPACES_MSG + "First Name.")
  private String firstName;

  @Schema(description = "The employee's last name")
  @Pattern(
      regexp = "\\s*\\p{Lu}\\p{Ll}*([\\s-]\\p{Lu}\\p{Ll}*)*\\s*",
      message = "Check the Last Name. " + WRONG_NAME_MSG)
  @Pattern(regexp = "\\S(.*\\S)*", message = REDUNDANT_WHITESPACES_MSG + "Last Name.")
  private String lastName;

  @Schema(description = "The department ID")
  @Min(value = 0, message = "Dept. ID cannot be negative")
  private Integer departmentId;

  @Schema(description = "The employee's position")
  private String jobTitle;

  @Schema(description = "The employee's gender (MALE/FEMALE)")
  private Gender gender;

  @JsonFormat(pattern = "yyyy-MM-dd")
  @Schema(description = "The employee's date of birth (yyyy-MM-dd)")
  @AgeRestriction(min = 18, message = "The employee should be at least 18 years old.")
  private LocalDate dateOfBirth;
}
