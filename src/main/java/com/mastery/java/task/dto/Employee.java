package com.mastery.java.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mastery.java.task.dto.validation.FullNameLanguageValid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Employee")
@FullNameLanguageValid(message = "The first and last names should use the same language (Belarusian, Russian and English are allowed)")
public class Employee {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "The employee ID", example = "0")
    private Integer id;

	@ApiModelProperty(value = "The employee first name", example = "John")
	@Pattern(regexp = "^\\p{Lu}\\p{Ll}*([\\s-]\\p{Lu}\\p{Ll}*)*", message = "Only letters (both cyrillic and latin) are allowed in a First Name." +
			"The words should be separated by whitespace or by hyphen. Every word should start with a capital letter.")
	@Pattern(regexp = "^\\S*$", message = "Delete redundant whitespaces in the First Name field.")
	private String firstName;

	@ApiModelProperty(value = "The employee last name", example = "Doe")
	@Pattern(regexp = "^\\p{Lu}\\p{Ll}*([\\s-]\\p{Lu}\\p{Ll}*)*", message = "Only letters (both cyrillic and latin) are allowed in a Last Name." +
			"The words should be separated by whitespace or by hyphen. Every word should start with a capital letter.")
	@Pattern(regexp = "^\\S*$", message = "Delete redundant whitespaces in the Last Name field.")
	private String lastName;

	@ApiModelProperty(value = "The department ID", example = "0")
	@Min(value = 0, message = "Dept. ID cannot be negative")
    private Integer departmentId;

	@ApiModelProperty(value = "The job title", example = "Programmer")
	private String jobTitle;

	@Type(type = "com.mastery.java.task.dto.EnumTypeSql")
	@ApiModelProperty(value = "The employee gender", example = "MALE")
	private Gender gender;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ApiModelProperty(value = "The employee date of birth", example = "1900-12-31")
	private LocalDate dateOfBirth;

	public Employee(String firstName, String lastName, int departmentId, String jobTitle, Gender gender, LocalDate dateOfBirth) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.departmentId = departmentId;
		this.jobTitle = jobTitle;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}
}
