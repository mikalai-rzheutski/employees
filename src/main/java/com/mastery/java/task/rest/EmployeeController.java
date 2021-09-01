package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Api(value = "Employee controller", description = "Контроллер для добавления, изменения и удаления информации о сотрудниках", tags = {"Сотрудники"})
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;

	@Operation(
			summary = "Все сотрудники",
			description = "Позволяет получить массив данных, содержащий информацию обо всех сотрудниках"
	)
	@GetMapping
	public List<Employee> fetchAllEmployees() {
		return employeeService.fetchAllEmployees();
	}

	@Operation(
			summary = "Получить информацию о сотруднике",
			description = "Позволяет получить информацию в формате json о сотруднике с заданным идентификатором"
	)
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployee(@PathVariable("id") @Parameter(description = "Id сотрудника") int id) {
		return new ResponseEntity<>(employeeService.getEmployee(id), HttpStatus.OK);
	}

	@Operation(
			summary = "Добавить нового сотрудника",
			description = "Позволяет сохранить информацию о новом сотруднике"
	)
	@PostMapping
	public ResponseEntity<Integer> createEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<>(employeeService.createEmployee(employee), HttpStatus.CREATED);
	}

	@Operation(
			summary = "Обновить информацию о сотруднике",
			description = "Позволяет обновить информацию о сотруднике"
	)
	@PutMapping("/{id}")
	public void updateEmployee(@PathVariable("id") @Parameter(description = "Id сотрудника") int id, @RequestBody Employee employee) {
		employeeService.updateEmployee(id, employee);
	}

	@Operation(
			summary = "Удалить информацию о сотруднике",
			description = "Позволяет удалить информацию о сотруднике"
	)
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable("id") @Parameter(description = "Id сотрудника") int id) {
		employeeService.deleteEmployee(id);
	}
}
