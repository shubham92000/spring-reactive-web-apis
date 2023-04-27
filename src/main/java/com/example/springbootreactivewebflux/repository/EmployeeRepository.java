package com.example.springbootreactivewebflux.repository;

import com.example.springbootreactivewebflux.entity.Employee;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, String> {

}
