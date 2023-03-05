package com.example.test.controller;

import com.example.test.dao.EmployeeRepo;
import com.example.test.payload.EmployeeResponse;
import com.example.test.service.EmployeeService;
import com.example.test.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;


    @GetMapping("/employees")
    public EmployeeResponse getAllPosts(
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return employeeService.getAllEmployees(address, pageNo, pageSize, sortBy, sortDir);
    }





}
