package com.altimetrik.springsecuritywithjwt.repository;

import com.altimetrik.springsecuritywithjwt.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findByEmployeeName(String employeeName);
}