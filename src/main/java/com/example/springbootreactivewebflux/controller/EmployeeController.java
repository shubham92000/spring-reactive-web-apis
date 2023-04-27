package com.example.springbootreactivewebflux.controller;

import com.example.springbootreactivewebflux.dto.EmployeeDto;
import com.example.springbootreactivewebflux.entity.Employee;
import com.example.springbootreactivewebflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.saveEmployee(employeeDto);
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") String id){
        return employeeService.getEmployee(id);
    }

    @GetMapping
    public Flux<EmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PutMapping("{id}")
    public Mono<EmployeeDto> updateEmp(@RequestBody EmployeeDto employeeDto,@PathVariable("id") String id){
        return employeeService.updateEmployee(employeeDto, id);
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteEmp(@PathVariable("id") String id){
        return employeeService.deleteEmployee(id);
    }
}
