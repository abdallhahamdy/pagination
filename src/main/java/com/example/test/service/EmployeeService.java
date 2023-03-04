package com.example.test.service;

import com.example.test.dao.EmployeeRepo;
import com.example.test.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    public Employee insert(Employee emp) {
        return employeeRepo.save(emp);
    }

    public Employee findById (Long id) {

        return employeeRepo.findById(id).orElseThrow();
    }


    public List<Employee> getUsersByPagination(int pageNo, int pageSize) {

//        Sort phoneSort = Sort.by("phone");
//        Sort nameSort = Sort.by("id");
//
//        Sort multiSort = phoneSort.and(nameSort);

        //create pagerequest object
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());

//        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, multiSort);

        //pass it to repos
        Page<Employee> pagingUser = employeeRepo.findAll(pageRequest);
        //pagingUser.hasContent(); -- to check pages are there or not




        return pagingUser.getContent();
    }
}
