package com.example.test.dao;

import com.example.test.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends PagingAndSortingRepository<Employee, Long> {

//    @Query(value = "select * from employee e where e.address=:address", nativeQuery = true)
//    List<Employee> findByAddress(@Param("address") String address);


    public List<Employee> findByAddressContaining(String address, Pageable pageable);

}
