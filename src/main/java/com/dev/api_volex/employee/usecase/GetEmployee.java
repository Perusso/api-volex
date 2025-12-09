package com.dev.api_volex.employee.usecase;

import com.dev.api_volex.employee.entity.EmployeeEntity;
import com.dev.api_volex.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class GetEmployee {

    private final EmployeeRepository employeeRepository;

    public GetEmployee(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public EmployeeEntity findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

}
