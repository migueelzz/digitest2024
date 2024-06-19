package com.digitest.api.domain.service;

import com.digitest.api.domain.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    @Value("${api.endpoint.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public EmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<EmployeeDTO> getAllEmployees() {
        EmployeeDTO[] employeesArray = restTemplate.getForObject(apiUrl, EmployeeDTO[].class);
        return Arrays.asList(employeesArray);
    }
}
