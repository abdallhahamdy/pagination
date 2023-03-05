package com.example.test.service;

import com.example.test.dao.EmployeeRepo;
import com.example.test.entity.Employee;
import com.example.test.payload.EmployeeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public EmployeeResponse getAllEmployees(String address, int page, int size, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

            List<Employee> employees = new ArrayList<Employee>();
            Pageable pagingSort = PageRequest.of(page, size, sort);

            Page<Employee> pageTuts;
            if (address == null)
                pageTuts = employeeRepo.findAll(pagingSort);
            else
                pageTuts = employeeRepo.findByAddressContaining(address, pagingSort);

            employees = pageTuts.getContent();


            EmployeeResponse employeeResponse = new EmployeeResponse();

            employeeResponse.setContent(employees);
            employeeResponse.setPage(pageTuts.getNumber());
            employeeResponse.setSize(pageTuts.getSize());
            employeeResponse.setTotalElements(pageTuts.getTotalElements());
            employeeResponse.setTotalPages(pageTuts.getTotalPages());

            return employeeResponse;


        }
}
