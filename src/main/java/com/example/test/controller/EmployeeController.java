package com.example.test.controller;

import com.example.test.dao.EmployeeRepo;
import com.example.test.entity.Employee;
import com.example.test.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    @GetMapping("/sortedemployees")
    public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Employee> employees = employeeRepo.findAll(Sort.by(orders));

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<Map<String, Object>> getAllEmployeesPage(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        try {
            List<Order> orders = new ArrayList<Order>();

            if (sort[0].contains(",")) {
                // will sort more than 2 fields
                // sortOrder="field, direction"
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                // sort=[field, direction]
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }

            List<Employee> employees = new ArrayList<Employee>();
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            Page<Employee> pageTuts;
            if (name == null)
                pageTuts = employeeRepo.findAll(pagingSort);
            else
                pageTuts = employeeRepo.findByNameContaining(name, pagingSort);

            employees = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("employees", employees);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/address")
    public ResponseEntity<Map<String, Object>> findByAddress(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        try {
            List<Employee> employees = new ArrayList<Employee>();
            Pageable paging = PageRequest.of(page, size);

            Page<Employee> pageTuts = employeeRepo.findByAddress("Cairo", paging);
            employees = pageTuts.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("employees", employees);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getTutorialById(@PathVariable("id") long id) {
        Optional<Employee> tutorialData = employeeRepo.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    

}
