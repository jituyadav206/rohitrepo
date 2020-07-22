package com.altimetrik.springsecuritywithjwt.service;

import com.altimetrik.springsecuritywithjwt.model.Employee;
import com.altimetrik.springsecuritywithjwt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class MyEmployeeUserDetailService implements UserDetailsService {


    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmployeeName(name);
        return new org.springframework.security.core.userdetails.User(employee.getEmployeeName(),employee.getPassword(),new ArrayList<>());
    }
}
