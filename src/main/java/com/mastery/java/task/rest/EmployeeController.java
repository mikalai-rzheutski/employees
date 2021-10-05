package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Api(value = "Employee REST controller", description = "REST controller", tags = {"Employees"})
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

	private final EmployeeService employeeService;

	@Operation(summary = "Gets all employees")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "List of employees",
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = Employee.class)))
					})
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Employee> fetchAllEmployees() {
		log();
		return employeeService.getAllEmployees();
	}

	@Operation(summary = "Finds Employee by ID")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "An Employee object",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(name = "Employee"))
					}),
			@ApiResponse(
					responseCode = "404",
					description = "Employee not found"
					)
	})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Employee getEmployee(@PathVariable("id") @Parameter(description = "ID of employee to find") int id) {
		log();
		return employeeService.getEmployee(id);
	}

	@Operation(summary = "Creates new Employee")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "The Employee was successfully created",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(description = "ID of the created Employee"))
					})
	})
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public int createEmployee(@Valid @RequestBody @Parameter(description = "Employee to be created", schema = @Schema(name = "Employee")) Employee employee) {
		log();
		return employeeService.createEmployee(employee);
	}

	@Operation(summary = "Updates existent Employee")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "The Employee was successfully updated"
			),
			@ApiResponse(
					responseCode = "404",
					description = "Employee not found"
			)
	})
	@PutMapping("/{id}")
	public void updateEmployee(@PathVariable("id") @Parameter(description = "ID of the employee to be updated") int id, @RequestBody @Parameter(description = "The new Employee", schema = @Schema(name = "Employee"))Employee employee) {
		log();
		employeeService.updateEmployee(id, employee);
	}

	@Operation(summary = "Deletes Employee by ID")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "The Employee was successfully deleted",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(name = "Employee"))
					}),
			@ApiResponse(
					responseCode = "404",
					description = "Employee not found"
			)
	})
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable("id") @Parameter(description = "ID of the employee to be deleted") int id) {
		log();
		employeeService.deleteEmployee(id);
	}

	private void log() {
		log.info("Method {} was called from controller {}",
				Thread.currentThread().getStackTrace()[2].getMethodName(),
				this.getClass().getName());
	}
}
