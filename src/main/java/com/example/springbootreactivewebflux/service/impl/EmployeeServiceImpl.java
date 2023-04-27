package com.example.springbootreactivewebflux.service.impl;

import com.example.springbootreactivewebflux.dto.EmployeeDto;
import com.example.springbootreactivewebflux.entity.Employee;
import com.example.springbootreactivewebflux.mapper.EmployeeMapper;
import com.example.springbootreactivewebflux.repository.EmployeeRepository;
import com.example.springbootreactivewebflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeMapper.mapToEntity(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);
        return savedEmployee.map((emp) -> EmployeeMapper.mapToDto(emp));
    }

    @Override
    public Mono<EmployeeDto> getEmployee(String empId) {
        Mono<Employee> savedEmployee = employeeRepository.findById(empId);
        return savedEmployee.map((emp) -> EmployeeMapper.mapToDto(emp));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployees() {
        Flux<Employee> employeeList = employeeRepository.findAll();
        return employeeList
                .map((emp) -> EmployeeMapper.mapToDto(emp))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto, String id) {
        Mono<Employee> employeeMono = employeeRepository.findById(id);
        Mono<Employee> updatedEmp = employeeMono.flatMap((existingEmp) -> {
            existingEmp.setFirstName(employeeDto.getFirstName());
            existingEmp.setLastName(employeeDto.getLastName());
            existingEmp.setEmail(employeeDto.getEmail());

            return employeeRepository.save(existingEmp);
        });
        return updatedEmp.map((emp) -> EmployeeMapper.mapToDto(emp));
    }

    @Override
    public Mono<Void> deleteEmployee(String id) {
        return employeeRepository.deleteById(id);
    }
}
