package com.example.test.payload;

import com.example.test.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    private List<Employee> content;
    private int page;
    private int size;
    private int currentPage;
    private long totalElements;
    private int totalPages;
}
