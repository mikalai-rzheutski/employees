package com.mastery.java.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private Integer id;

	private String firstName;

	private String lastName;

    private Integer departmentId;

	private String jobTitle;

	private Gender gender;

	@JsonFormat(pattern = "yyyy-MM-dd")
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