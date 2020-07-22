package com.altimetrik.springsecuritywithjwt;

import com.altimetrik.springsecuritywithjwt.model.Employee;
import com.altimetrik.springsecuritywithjwt.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringsecuritywithjwtApplication {

	@Autowired
	EmployeeRepository employeeRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringsecuritywithjwtApplication.class, args);
	}

	@PostConstruct
	public void init(){

		List<Employee> employeeList = Stream.of(
				new Employee(101, "rohit", "1234"),
				new Employee(102, "jitu", "4321")
		).collect(Collectors.toList());

		employeeRepository.saveAll(employeeList);

	}

}
