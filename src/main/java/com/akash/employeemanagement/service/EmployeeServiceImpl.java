package com.akash.employeemanagement.service;

import com.akash.employeemanagement.entity.Employee;
import com.akash.employeemanagement.exception.DuplicateEmailException;
import com.akash.employeemanagement.repository.EmployeeRepository;
import com.akash.employeemanagement.exception.EmployeeNotFoundException;
import com.akash.employeemanagement.exception.InvalidSortException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {

        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new DuplicateEmailException(
                    "Employee with email " + employee.getEmail() + " already exists"
            );
        }

        return employeeRepository.save(employee);
    }
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee with id " + id + " not found"
                        )
                );
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee with id " + id + " not found"
                        )
                );

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee with id " + id + " not found"
                        )
                );

        employeeRepository.delete(existingEmployee);
    }

    @Override
    public Page<Employee> getEmployeesWithPagination(
            int page,
            int size,
            String sort) {

        String[] sortParams = sort.split(",");

        if (sortParams.length != 2) {
            throw new InvalidSortException(
                    "Sort must be in the format: field,direction"
            );
        }

        String field = sortParams[0];
        String direction = sortParams[1];

        if (!field.equals("id")
                && !field.equals("firstName")
                && !field.equals("lastName")
                && !field.equals("email")
                && !field.equals("department")
                && !field.equals("salary")) {

            throw new InvalidSortException(
                    "Invalid sort field: " + field
            );
        }

        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {

            throw new InvalidSortException(
                    "Sort direction must be 'asc' or 'desc'"
            );
        }

        Sort.Direction sortDirection =
                Sort.Direction.fromString(direction);

        Sort sorting = Sort.by(sortDirection, field);

        Pageable pageable = PageRequest.of(
                page,
                size,
                sorting
        );

        return employeeRepository.findAll(pageable);
    }

    @Override
    public Page<Employee> getEmployeesByDepartment(
            String department,
            int page,
            int size,
            String sort) {

        String[] sortParams = sort.split(",");

        if (sortParams.length != 2) {
            throw new InvalidSortException(
                    "Sort must be in the format: field,direction"
            );
        }

        String field = sortParams[0];
        String direction = sortParams[1];

        if (!field.equals("id")
                && !field.equals("firstName")
                && !field.equals("lastName")
                && !field.equals("email")
                && !field.equals("department")
                && !field.equals("salary")) {

            throw new InvalidSortException(
                    "Invalid sort field: " + field
            );
        }

        if (!direction.equalsIgnoreCase("asc")
                && !direction.equalsIgnoreCase("desc")) {

            throw new InvalidSortException(
                    "Sort direction must be 'asc' or 'desc'"
            );
        }

        Sort.Direction sortDirection =
                Sort.Direction.fromString(direction);

        Sort sorting = Sort.by(sortDirection, field);

        Pageable pageable = PageRequest.of(
                page,
                size,
                sorting
        );

        return employeeRepository.findByDepartment(
                department,
                pageable
        );
    }
}