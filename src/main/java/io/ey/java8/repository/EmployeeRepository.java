package io.ey.java8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ey.java8.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
