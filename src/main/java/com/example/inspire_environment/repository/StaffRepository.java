package com.example.inspire_environment.repository;

import com.example.inspire_environment.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    Optional<Staff> findByEmail(String email);

    List<Staff> findByDepartmentId(Long departmentId);

    List<Staff> findByPosition(String position);

    @Query("SELECT s FROM Staff s WHERE s.firstName LIKE %:name% OR s.lastName LIKE %:name%")
    List<Staff> findByNameContaining(String name);
}
