package com.example.springbootreactivewebflux;

import com.example.springbootreactivewebflux.controller.EmployeeController;
import com.example.springbootreactivewebflux.dto.EmployeeDto;
import com.example.springbootreactivewebflux.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = EmployeeController.class)
public class EmployeeControllerTests {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void givenEmpObj_whenSaveEmp_thenReturnSavedEmp(){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("shubham");
        employeeDto.setLastName("singh");
        employeeDto.setEmail("s@gmail.com");

        BDDMockito.given(employeeService.saveEmployee(ArgumentMatchers.any(EmployeeDto.class)))
                .willReturn(Mono.just(employeeDto));

        WebTestClient.ResponseSpec response = webTestClient.post().uri("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(employeeDto), EmployeeDto.class)
                .exchange();

        response.expectStatus().isCreated()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail())
                ;
    }

    @Test
    public void givenEmpId_whenGetEmp_thenReturnEmpObj(){
        String empId = "123";
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("shubham");
        employeeDto.setLastName("singh");
        employeeDto.setEmail("s@gmail.com");

        BDDMockito.given(employeeService.getEmployee(empId))
                .willReturn(Mono.just(employeeDto));

        WebTestClient.ResponseSpec response = webTestClient.get()
                .uri("/api/employees/{id}", Collections.singletonMap("id", empId))
                .exchange();

        response.expectStatus().isOk()
                .expectBody()
                .consumeWith(System.out::println)
                .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
                .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
                .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
    }

    @Test
    public void givenListOfEmp_whenGetAllEmp_returnListOfEmp(){
        List<EmployeeDto> list = new ArrayList<>();
        EmployeeDto e1 = new EmployeeDto();
        e1.setFirstName("shubham");
        e1.setLastName("singh");
        e1.setEmail("s@gmail.com");
        list.add(e1);

        EmployeeDto e2 = new EmployeeDto();
        e2.setFirstName("john");
        e2.setLastName("doe");
        e2.setEmail("john@gmail.com");
        list.add(e2);

        Flux<EmployeeDto> employeeDtoFlux = Flux.fromIterable(list);

        BDDMockito.given(employeeService.getAllEmployees())
                .willReturn(employeeDtoFlux);

        WebTestClient.ResponseSpec responseSpec = webTestClient.get().uri("/api/employees")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                ;

        responseSpec.expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .consumeWith(System.out::println)
                ;
    }
}

