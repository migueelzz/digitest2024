package com.digitest.api.domain.controller;

import com.digitest.api.domain.dto.EmployeeDTO;
import com.digitest.api.domain.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<Map<String, Long>> countGenders() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        // Contagem de gêneros
        Map<String, Long> genderCounts = employees.stream()
                .collect(Collectors.groupingBy(EmployeeDTO::gender, Collectors.counting()));

        return ResponseEntity.status(HttpStatus.OK).body(genderCounts);
    }
}
