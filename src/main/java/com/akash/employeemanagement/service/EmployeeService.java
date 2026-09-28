package com.akash.employeemanagement.service;

import com.akash.employeemanagement.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);

    Page<Employee> getEmployeesWithPagination(
            int page,
            int size,
            String sort
    );

    Page<Employee> getEmployeesByDepartment(
            String department,
            int page,
            int size,
            String sort
    );
}