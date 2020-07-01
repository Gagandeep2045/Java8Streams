package io.ey.java8.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.ey.java8.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}
