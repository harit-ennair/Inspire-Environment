package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentCode(String studentCode);

    Optional<Student> findByEmail(String email);

    List<Student> findByDepartmentId(Long departmentId);

//    @Query("SELECT s FROM Student s WHERE s.firstName LIKE %:name% OR s.lastName LIKE %:name%")
//    List<Student> findByNameContaining(String name);

    List<Student> findByFirstNameContainingIgnoreCase(String name);
}
