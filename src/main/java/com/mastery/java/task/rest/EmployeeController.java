package com.mastery.java.task.rest;

import com.mastery.java.task.exception.ErrorMessage;
import com.mastery.java.task.jms.JmsProducer;
import com.mastery.java.task.model.dtos.employee.EmployeeDto;
import com.mastery.java.task.model.entities.employee.Employee;
import com.mastery.java.task.service.EmployeeService;
import com.mastery.java.task.util.Util;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@Tag(description = "Employee REST controller", name = "Employees")
@RequiredArgsConstructor
@Validated
@Log4j2
public class EmployeeController {

  private static final String NEGATIVE_ID_MSG = "Id should be nonnegative";

  private final EmployeeService employeeService;

  private final ModelMapper modelMapper;

  private final JmsProducer jmsProducer;

  @Operation(
      summary = "Gets list of employees filtered by parameters. ",
      description =
          "Default behaviour is a strict filtering "
              + "(a non-strict filtering can be switched on using nonStrict parameter). \n"
              + "All the parameters are optional (filtering by an absent parameter is not performed).",
      parameters = {
        @Parameter(in = ParameterIn.QUERY, name = "id", description = "Employee's id"),
        @Parameter(
            in = ParameterIn.QUERY,
            name = "firstName",
            description = "Employee's first name"),
        @Parameter(in = ParameterIn.QUERY, name = "lastName", description = "Employee's last name"),
        @Parameter(in = ParameterIn.QUERY, name = "departmentId", description = "Department's id"),
        @Parameter(in = ParameterIn.QUERY, name = "jobTitle", description = "Employee's position"),
        @Parameter(
            in = ParameterIn.QUERY,
            name = "gender",
            description = "Employee's gender (male/female)"),
        @Parameter(
            in = ParameterIn.QUERY,
            name = "dateOfBirth",
            description = "Employee's date of birth (yyyy-MM-dd)"),
        @Parameter(
            in = ParameterIn.QUERY,
            name = "nonStrict",
            description = "Turns on the non-strict filtering mode if set to \"yes\".")
      })
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of employees",
            content = {
              @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = EmployeeDto.class)))
            })
      })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public List<EmployeeDto> getEmployees(
      @Parameter(hidden = true) @RequestParam Map<String, String> searchParams) {
    logMethodIn("getEmployees", searchParams);
    List<EmployeeDto> employeeDtoList =
        employeeService.getEmployees(searchParams).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    logMethodOut("getEmployees");
    return employeeDtoList;
  }

  @Operation(summary = "Finds Employee by ID")
  @ApiResponses(
      value = {
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
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class))
            })
      })
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public EmployeeDto getEmployee(
      @PathVariable("id")
          @Parameter(description = "ID of employee to find")
          @Min(value = 0, message = NEGATIVE_ID_MSG)
          int id) {
    logMethodIn("getEmployee", id);
    EmployeeDto employeeDto = convertToDto(employeeService.getEmployee(id));
    logMethodOut("getEmployee");
    return employeeDto;
  }

  @Operation(summary = "Creates new Employee")
  @ApiResponses(
      value = {
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
            })
      })
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.CREATED)
  public int createEmployee(
      @Valid
          @RequestBody
          @Parameter(description = "Employee to be created", schema = @Schema(name = "Employee"))
          EmployeeDto employeeDto) {
    logMethodIn("createEmployee", employeeDto);
    int id = employeeService.createEmployee(convertToEntity(employeeDto)).getId();
    logMethodOut("createEmployee");
    return id;
  }

  @Operation(summary = "Updates existent Employee")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "The Employee was successfully updated"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid parameter(s)",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class))
            })
      })
  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void updateEmployee(
      @PathVariable("id")
          @Parameter(description = "ID of the employee to be updated")
          @Min(value = 0, message = NEGATIVE_ID_MSG)
          int id,
      @Valid
          @RequestBody
          @Parameter(description = "The new Employee", schema = @Schema(name = "Employee"))
          EmployeeDto employeeDto) {
    logMethodIn("updateEmployee", id, employeeDto);
    employeeService.throwExceptionIfNotFound(
        id, "updated"); // it is just for compatibility with Swagger and tests
    employeeDto.setId(id);
    jmsProducer.updateEmployee(convertToEntity(employeeDto));
    //	employeeService.updateEmployee(convertToEntity(employeeDto));
    logMethodOut("updateEmployee");
  }

  @Operation(summary = "Deletes Employee by ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "The Employee was successfully deleted"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid parameter(s)",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorMessage.class))
            })
      })
  @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteEmployee(
      @PathVariable("id")
          @Parameter(description = "ID of the employee to be deleted")
          @Min(value = 0, message = NEGATIVE_ID_MSG)
          int id) {
    logMethodIn("deleteEmployee", id);
    employeeService.deleteEmployee(id);
    logMethodOut("deleteEmployee");
  }

  private EmployeeDto convertToDto(Employee employee) {
    return modelMapper.map(employee, EmployeeDto.class);
  }

  private Employee convertToEntity(EmployeeDto employeeDto) {
    return modelMapper.map(employeeDto, Employee.class);
  }

  private void logMethodIn(String methodName, Object... args) {
    log.debug("METHOD IN: {}", () -> Util.asJsonString(new MethodInfo(methodName, args)));
  }

  private void logMethodOut(String methodName) {
    log.debug("METHOD OUT: {}", methodName);
  }

  @Data
  @AllArgsConstructor
  private class MethodInfo {

    private String methodName;

    private Object[] arguments;
  }
}
