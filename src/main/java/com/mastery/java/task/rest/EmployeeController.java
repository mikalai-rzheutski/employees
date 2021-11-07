package com.mastery.java.task.rest;

import com.mastery.java.task.exception.ErrorMessage;
import com.mastery.java.task.jms.JmsProducer;
import com.mastery.java.task.logger.annotations.LogMethodCall;
import com.mastery.java.task.model.dtos.employee.EmployeeDto;
import com.mastery.java.task.model.entities.employee.Employee;
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
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Api(value = "Employee REST controller", tags = {"Employees"})
@RequiredArgsConstructor
@Validated
public class EmployeeController {

	private final EmployeeService employeeService;

    private final ModelMapper modelMapper;

    private final JmsProducer jmsProducer;

    private static final String NEGATIVE_ID_MSG = "Id should be nonnegative";

    @Operation(summary = "Gets all employees")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "List of employees",
					content = {
							@Content(
									mediaType = "application/json",
									array = @ArraySchema(schema = @Schema(implementation = EmployeeDto.class)))
					})
	})
    @LogMethodCall
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDto> fetchAllEmployees() {
        return employeeService.getAllEmployees().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Operation(summary = "Finds Employee by ID")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "An Employee object",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(implementation = EmployeeDto.class))
					}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parameter(s)",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    }
            ),
			@ApiResponse(
					responseCode = "404",
					description = "Employee not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    })
	})
    @LogMethodCall
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDto getEmployee(@PathVariable("id") @Parameter(description = "ID of employee to find")
                                   @Min(value = 0, message = NEGATIVE_ID_MSG) int id) {
        return convertToDto(employeeService.getEmployee(id));
	}

	@Operation(summary = "Creates new Employee")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Id of the created Employee",
					content = {
							@Content(
									mediaType = "application/json",
									schema = @Schema(implementation = Integer.class))
					}),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parameter(s)",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    }
            )
	})
    @LogMethodCall
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
    public int createEmployee(@Valid @RequestBody
                              @Parameter(description = "Employee to be created", schema = @Schema(name = "Employee"))
                                      EmployeeDto employeeDto) {
        return employeeService.createEmployee(convertToEntity(employeeDto)).getId();
	}

	@Operation(summary = "Updates existent Employee")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "The Employee was successfully updated"
			),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid parameter(s)",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    }
			),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    }
            )
	})
    @LogMethodCall
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateEmployee(@PathVariable("id") @Parameter(description = "ID of the employee to be updated")
                               @Min(value = 0, message = NEGATIVE_ID_MSG) int id,
                               @Valid @RequestBody
                               @Parameter(description = "The new Employee", schema = @Schema(name = "Employee"))
                                       EmployeeDto employeeDto) {
		employeeService.throwExceptionIfNotFound(id, "updated"); // it is just for compatibility with Swagger and tests
    	employeeDto.setId(id);
		jmsProducer.updateEmployee(convertToEntity(employeeDto));
    //	employeeService.updateEmployee(convertToEntity(employeeDto));
	}

	@Operation(summary = "Deletes Employee by ID")
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "The Employee was successfully deleted"
					),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parameter(s)",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    }
            ),
			@ApiResponse(
					responseCode = "404",
					description = "Employee not found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    }
			)
	})
    @LogMethodCall
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEmployee(@PathVariable("id") @Parameter(description = "ID of the employee to be deleted")
                               @Min(value = 0, message = NEGATIVE_ID_MSG) int id) {
		employeeService.deleteEmployee(id);
	}

    private EmployeeDto convertToDto(Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }

    private Employee convertToEntity(EmployeeDto employeeDto) {
        return modelMapper.map(employeeDto, Employee.class);
	}
}
