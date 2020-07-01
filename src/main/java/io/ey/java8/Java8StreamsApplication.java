package io.ey.java8;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import io.ey.java8.domain.Department;
import io.ey.java8.domain.Employee;
import io.ey.java8.repository.DepartmentRepository;
import io.ey.java8.repository.EmployeeRepository;

@SpringBootApplication
public class Java8StreamsApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(Java8StreamsApplication.class, args);
		EmployeeRepository employeeRepository = ac.getBean(EmployeeRepository.class);
		DepartmentRepository documentRepository = ac.getBean(DepartmentRepository.class);
		List<Employee> empList = employeeRepository.findAll();
		System.out.println("empList: " + empList);
		System.out.println("--------------------------------");
		List<Department> deptList = documentRepository.findAll();
		System.out.println("deptList: " + deptList);

	}

}
