package com.mastery.java.task.model.dtos.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mastery.java.task.model.Gender;
import com.mastery.java.task.model.dtos.employee.validation.age.AgeRestriction;
import com.mastery.java.task.model.dtos.employee.validation.fullname.ValidFullNameLanguage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
        message = "The first and last names should use one language only (Belarusian, Russian and English are allowed)")
@ApiModel("Employee")
public class EmployeeDto {

    private static final String WRONG_NAME_MSG =
            "It should consist of one or several words separated by whitespaces or hyphens. " +
            "Every word should start with a capital letter. " + "Only letters (both cyrillic and latin) are allowed.";

    private static final String REDUNDANT_WHITESPACES_MSG = "Delete redundant whitespaces in the ";

    @ApiModelProperty(value = "The employee ID", example = "0")
    private Integer id;

    @ApiModelProperty(value = "The employee's first name", example = "John")
    @Pattern(regexp = "\\s*\\p{Lu}\\p{Ll}*([\\s-]\\p{Lu}\\p{Ll}*)*\\s*",
             message = "Check the First Name. " + WRONG_NAME_MSG)
    @Pattern(regexp = "\\S(.*\\S)*", message = REDUNDANT_WHITESPACES_MSG + "First Name.")
    private String firstName;

    @ApiModelProperty(value = "The employee's last name", example = "Doe")
    @Pattern(regexp = "\\s*\\p{Lu}\\p{Ll}*([\\s-]\\p{Lu}\\p{Ll}*)*\\s*",
             message = "Check the Last Name. " + WRONG_NAME_MSG)
    @Pattern(regexp = "\\S(.*\\S)*", message = REDUNDANT_WHITESPACES_MSG + "Last Name.")
    private String lastName;

    @ApiModelProperty(value = "The department ID", example = "0")
    @Min(value = 0, message = "Dept. ID cannot be negative")
    private Integer departmentId;

    @ApiModelProperty(value = "The job title", example = "Programmer")
    private String jobTitle;

    @ApiModelProperty(value = "The employee's gender", example = "MALE")
    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "The employee's date of birth", example = "1900-12-31")
    @AgeRestriction(min = 18, message = "The employee should be at least 18 years old.")
    private LocalDate dateOfBirth;

}
