package com.example.test.controller;

import com.example.test.dao.EmployeeRepo;
import com.example.test.entity.Employee;
import com.example.test.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @PostMapping("/employees")
    public ResponseEntity<Employee> save (@RequestBody Employee employee)
    {
        logger.info("Post");

        return new ResponseEntity<Employee>(employeeRepo.save(employee), HttpStatus.OK);
    }

//    @GetMapping("/employee")
//    public ResponseEntity<List<Employee>> findAllTransaction() {
//        logger.info("Get");
//
//        return new ResponseEntity<List<Employee>>(employeeRepo.findAll(), HttpStatus.OK);
//
//    }

    @GetMapping("/employee/{id}")
    public Employee findByEmp (@PathVariable Long id) {

        return employeeService.findById(id);
    }


//    @GetMapping("/filter")
//    public List<Employee> findByName (@RequestParam String address) {
//
//        return employeeRepo.findByAddress(address);
//    }

    @GetMapping
    public List<Employee> getUserWithPaging(@RequestParam(defaultValue = "0") Integer pageNo,
                                            @RequestParam(defaultValue = "10") Integer pageSize){

        return employeeService.getUsersByPagination(pageNo,pageSize);

    }
}
