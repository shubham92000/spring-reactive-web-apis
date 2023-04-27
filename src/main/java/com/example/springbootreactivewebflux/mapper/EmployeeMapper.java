package com.example.springbootreactivewebflux.mapper;

import com.example.springbootreactivewebflux.dto.EmployeeDto;
import com.example.springbootreactivewebflux.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto mapToDto(Employee employee){
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    public static Employee mapToEntity(EmployeeDto employeeDto){
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }
}
