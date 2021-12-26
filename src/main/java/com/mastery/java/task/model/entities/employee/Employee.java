package com.mastery.java.task.model.entities.employee;

import com.mastery.java.task.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String firstName;

  private String lastName;

  private Integer departmentId;

  private String jobTitle;

  @Type(type = "com.mastery.java.task.model.entities.employee.EnumTypeSql")
  private Gender gender;

  private LocalDate dateOfBirth;
}
