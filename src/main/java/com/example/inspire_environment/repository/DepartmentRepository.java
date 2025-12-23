package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
